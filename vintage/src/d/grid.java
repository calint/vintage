package d;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
final class grid{
	protected final int colbit_struct=1;
	protected final int colbit_vehicle=1;
	protected final int colbit_bullet=1;
	
	final static List<obj>all=new LinkedList<obj>();
	static void put(final obj o){all.add(o);}
	static void put(final Collection<obj>col){all.addAll(col);}
	static void rem(final obj o){all.remove(o);}
	static void rem(final Collection<obj>col){all.removeAll(col);}
	static void update()throws Throwable{for(final obj o:all)o.update();}
	static void render()throws Throwable{for(final obj o:all)o.render();}
	static void coldet()throws Throwable{
		final int n=all.size();
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
	static long benchniter=1024;
	static void bench(){
		final long t0=System.nanoTime();
		for(long j=0;j<benchniter;j++){
			for(Iterator<obj>i=all.iterator();i.hasNext();){
				final obj o=i.next();
				o.pos.x=o.pos.x;
			}			
		}
		final long t1=System.nanoTime();
		final long dt=t1-t0;
		System.out.println(" grid bench iter "+dt+" ns");
	}
}
