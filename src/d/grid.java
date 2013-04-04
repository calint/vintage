package d;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import d.box.mtrs;
final class grid{
		//layers
	protected static final int bit_struct=1;
	protected static final int bit_vehicle=2;
	protected static final int bit_bullet=4;
	protected static final int bit_static=8;
	protected static final int bit_scenery=16;
	final static grid o=new grid(2f,p.n(0,0,.5f));
	final static List<obj>objs=new LinkedList<obj>();
	static int splitthresh=50;//if a grid contains more than thresh the grid is split in 8 cubes
	static int subgridlevels=4;//maximum splits
	static int ngrids;//grid count
	final p po;//grid origo
	final float s;//grid scale // r=
	final List<obj>ls=new ArrayList<obj>();
	final grid[]grids=new grid[8];//sub grids, if [0]!=null
	static Collection<obj>news=new ArrayList<obj>();
	//	private static Collection<obj>all=new ArrayList<obj>();
	static Collection<obj>dels=new ArrayList<obj>();
	grid(final float size,final p po){this.po=po;this.s=size;ngrids++;}
	void cullrend(final pn[]cullpns){
		for(int i=0;i<cullpns.length;i++){
			final pn pn=cullpns[i];
			final float d=pn.disttopoint(po);
			if(d-s>0){//? s!=r
				mtrs.ngridcull++;
				return;
			}
		}
		mtrs.ngridrend++;
		for(final obj o:ls)
			o.cullrend(cullpns);
		
		if(grids[0]!=null)
			for(final grid g:grids)
				g.cullrend(cullpns);
	}
	void clear(){
		ls.clear();
		if(grids[0]!=null){
			for(final grid c:grids)
				c.clear();
			for(int i=0;i<grids.length;i++)
				grids[i]=null;
		}
	}
	void addall(final Collection<obj>col){
		boolean put=false;
		for(final obj o:col)
			if(putif(o,o.pos,o.radius))
				put=true;
		if(!put)throw new Error();
		splitif(subgridlevels);//? splititonthefly
		//? ifallglobswhereaddedtoallsubgrids,stoprecurtion
	}
	private boolean putif(final obj o,final/*reaadonly*/p p,final float r){
		if((p.x+s+r)<po.x)return false;
		if((p.x-s-r)>po.x)return false;
		if((p.z+s+r)<po.z)return false;
		if((p.z-s-r)>po.z)return false;
		if((p.y+s+r)<po.y)return false;
		if((p.y-s-r)>po.y)return false;
		ls.add(o);
		return true;
	}
	private boolean splitif(final int nrec){
		if(ls.size()<splitthresh)
			return false;
		if(nrec==0)
			return false;
		final float ns=s/2;
		grids[0]=new grid(ns,po.clone().add(-ns,ns,-ns));
		grids[1]=new grid(ns,po.clone().add( ns,ns,-ns));
		grids[2]=new grid(ns,po.clone().add(-ns,ns, ns));
		grids[3]=new grid(ns,po.clone().add( ns,ns, ns));

		grids[4]=new grid(ns,po.clone().add(-ns,-ns,-ns));
		grids[5]=new grid(ns,po.clone().add( ns,-ns,-ns));
		grids[6]=new grid(ns,po.clone().add(-ns,-ns, ns));
		grids[7]=new grid(ns,po.clone().add( ns,-ns, ns));
	
		for(final grid c:grids){
			for(final obj o:ls){
				c.putif(o,o.pos,o.radius);
			}
			c.splitif(nrec-1);
		}
		ls.clear();
		return true;
	}
	void coldet()throws Throwable{
		coldet(ls);
		
		//? multithread
//		box.thdpool.execute(new Runnable(){public void run(){try{
//			coldet(ls);
//		}catch(final Throwable t){throw new Error(t);}}});
		
//		new Thread(new Runnable(){public void run(){try{
//			coldet(ls);
//		}catch(final Throwable t){throw new Error(t);}}}).start();
		if(grids[0]!=null){
			for(final grid c:grids)
				c.coldet();
		}
	}
	void update()throws Throwable{
		for(final obj o:ls)o.upd();
		if(grids[0]!=null)
			for(final grid c:grids)
				c.update();
	}
	static void updaterender(final pn[]cullpns)throws Throwable{
			final long t0=System.currentTimeMillis();
			rem(dels);
			dels.clear();
			put(news);
			news.clear();
			o.clear();
			ngrids=0;
			o.addall(objs);
	//		System.out.println("ngrids:"+(grids.ngrids+1));
			final long t1=System.currentTimeMillis();
			o.cullrend(cullpns);
			final long t2=System.currentTimeMillis();
			o.update();
			final long t3=System.currentTimeMillis();
			o.coldet();
			final long t4=System.currentTimeMillis();		
			box.mtrs.ms_gridupd=t1-t0;
			box.mtrs.ms_render=t2-t1;
			box.mtrs.ms_update=t3-t2;
			box.mtrs.ms_coldet=t4-t3;
		}
	private static void put(final Collection<obj>col){objs.addAll(col);}
	private static void rem(final Collection<obj>col){objs.removeAll(col);}
	private static void coldet(final List<obj>all)throws Throwable{
		final int n=all.size();
		if(n<2)return;
		for(int i=0;i<n-1;i++){
			for(int j=i+1;j<n;j++){
				final obj o1=all.get(i);
				final obj o2=all.get(j);
				final int a1=o1.colbits&o2.colbitsmsk;//does o1 want oncol from o2?
				final int a2=o2.colbits&o1.colbitsmsk;//does o2 want oncol from o1?
				if(a1==0&&a2==0)
					continue;
				final boolean yes=obj.isincol(o1,o2);
				if(yes){
					box.mtrs.noncols++;
					if(a1!=0){
						o1.oncol(o2);
					}
					if(a2!=0){
						o2.oncol(o1);
					}
				}
			}
		}	
	}
}