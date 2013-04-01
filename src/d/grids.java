package d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class grids{
		static int splitthresh=300;
		static int subgridlevels=4;
		final p po;
		final float s;
		final List<obj>ls=new ArrayList<obj>();
		final grids[]cubes=new grids[8];
		grids(final float size,final p po){this.po=po;this.s=size;mtrs.ngrids++;}
		void render()throws Throwable{
			//if not in or intersecting viewpyr return
			
			for(final obj o:ls)o.render();
			if(cubes[0]!=null)
				for(final grids c:cubes)
					c.render();
		}
		void clear(){
			ls.clear();
			if(cubes[0]!=null){
				for(final grids c:cubes)
					c.clear();
				for(int i=0;i<cubes.length;i++)cubes[i]=null;
			}
		}
		void addall(final Collection<obj>col){
			for(final obj o:col){
				putif(o,o.pos,o.radius);
			}
			splitif(subgridlevels);//? splititonthefly
//			//? ifallglobswhereaddedtoallsubgrids,stoprecurtion
		}
		private boolean putif(final obj o,final p p,final float r){
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
			cubes[0]=new grids(ns,po.clone().add(-ns,ns,-ns));//?
			cubes[1]=new grids(ns,po.clone().add( ns,ns,-ns));
			cubes[2]=new grids(ns,po.clone().add(-ns,ns, ns));
			cubes[3]=new grids(ns,po.clone().add( ns,ns, ns));
		//
			cubes[4]=new grids(ns,po.clone().add(-ns,-ns,-ns));
			cubes[5]=new grids(ns,po.clone().add( ns,-ns,-ns));
			cubes[6]=new grids(ns,po.clone().add(-ns,-ns, ns));
			cubes[7]=new grids(ns,po.clone().add( ns,-ns, ns));
		
			for(final grids c:cubes){
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
			if(cubes[0]!=null)
				for(final grids c:cubes)
					c.coldet();
		}
		void update()throws Throwable{
			for(final obj o:ls)o.update();
			if(cubes[0]!=null)
				for(final grids c:cubes)
					c.update();
		}
		
		
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