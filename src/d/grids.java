package d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

final class grids{
		static int splitthresh=20;
		static int subgridlevels=4;
		static int ngrids;
		final p po;
		final float s;
		final List<obj>ls=new ArrayList<obj>();
		final grids[]grids=new grids[8];
		protected final int bit_bullet=4;
		protected final int bit_struct=1;
		protected final int bit_vehicle=2;
		final static List<obj>all=new LinkedList<obj>();
		grids(final float size,final p po){this.po=po;this.s=size;ngrids++;}
		void render()throws Throwable{
			//if not in or intersecting viewpyr return
			
			for(final obj o:ls)o.render();
			if(grids[0]!=null)
				for(final grids g:grids)
					g.render();
		}
		void clear(){
			ls.clear();
			if(grids[0]!=null){
				for(final grids c:grids)
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
//			//? ifallglobswhereaddedtoallsubgrids,stoprecurtion
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
			grids[0]=new grids(ns,po.clone().add(-ns,ns,-ns));
			grids[1]=new grids(ns,po.clone().add( ns,ns,-ns));
			grids[2]=new grids(ns,po.clone().add(-ns,ns, ns));
			grids[3]=new grids(ns,po.clone().add( ns,ns, ns));

			grids[4]=new grids(ns,po.clone().add(-ns,-ns,-ns));
			grids[5]=new grids(ns,po.clone().add( ns,-ns,-ns));
			grids[6]=new grids(ns,po.clone().add(-ns,-ns, ns));
			grids[7]=new grids(ns,po.clone().add( ns,-ns, ns));
		
			for(final grids c:grids){
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
			if(grids[0]!=null)
				for(final grids c:grids)
					c.coldet();
		}
		void update()throws Throwable{
			for(final obj o:ls)o.upd();
			if(grids[0]!=null)
				for(final grids c:grids)
					c.update();
		}
		
		
		//	static void rem(final obj o){all.remove(o);}
			static void rem(final Collection<obj>col){all.removeAll(col);}
		//	static void update()throws Throwable{for(final obj o:all)o.update();}
		//	static void render()throws Throwable{for(final obj o:all)o.render();}
		//	static void coldet()throws Throwable{coldet(all);}
		//	static void coldet(final List<obj>all)throws Throwable{
		//		final int n=all.size();
		//		if(n<2)return;
		//		for(int i=0;i<n-1;i++){
		//			for(int j=i+1;j<n;j++){
		//				final obj o1=all.get(i);
		//				final obj o2=all.get(j);
		//				final int a1=o1.colbits&o2.colbitsmsk;//does o1 want oncol from o2?
		//				final int a2=o2.colbits&o1.colbitsmsk;//does o2 want oncol from o1?
		//				if(a1==0&&a2==0)
		//					continue;
		//				final boolean yes=obj.isincol(o1,o2);
		//				if(yes){
		//					mtrs.noncols++;
		//					if(a1!=0){
		//						o1.oncol(o2);
		//					}
		//					if(a2!=0){
		//						o2.oncol(o1);
		//					}
		//				}
		//			}
		//		}	
		//	}
		//	static long benchniter=1024;
		//	static void bench(){
		//		final long t0=System.nanoTime();
		//		for(long j=0;j<benchniter;j++){
		//			for(Iterator<obj>i=all.iterator();i.hasNext();){
		//				final obj o=i.next();
		//				o.pos.x=o.pos.x;
		//			}			
		//		}
		//		final long t1=System.nanoTime();
		//		final long dt=t1-t0;
		//		System.out.println(" grid bench iter "+dt+" ns");
		//	}
		//	static void put(final obj o){all.add(o);}
		static void put(final Collection<obj>col){all.addAll(col);}
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
						mtrs.noncols++;
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