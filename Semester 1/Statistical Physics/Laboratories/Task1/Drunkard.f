      implicit none
      integer i,d,a,N,step,x,j,K,sigma
      parameter(N=1000)!steps
      parameter(K=10000)!number of drunkards
      real r,ran1,xNa,xN2a
      
      d=-1
      x=0
      j=0
      xNa=0
      xN2a=0
      sigma=0
      !prepare measurements for N=
      !100,500,1000,1500,2000,3000,5000,7000,10000
      open(1,file='dataN1000.csv')
      
      do j=1,K
      x=0
        do step=1,N
           if (ran1(d) > 0.5) then
               x=x+1
           else
               x=x-1
           end if
        enddo
        write( 1, * ) x
        !x jest tutaj sum krok¢w danego marynarza
        xNa=xNa+x
        xN2a=xN2a+x*x
      enddo
      sigma=sqrt(xN2a/float(K)-(xNa/float(K))*(xNa/float(K)))
      write(1,*) '--------------'
      write( 1, * ) sigma
      
      close(1)
      end

      FUNCTION ran1(idum)
      INTEGER idum,IA,IM,IQ,IR,NTAB,NDIV
      REAL ran1,AM,EPS,RNMX
      PARAMETER (IA=16807,IM=2147483647,AM=1./IM,IQ=127773,IR=2836,
     *NTAB=32,NDIV=1+(IM-1)/NTAB,EPS=1.2e-7,RNMX=1.-EPS)
      INTEGER j,k,iv(NTAB),iy
      SAVE iv,iy
      DATA iv /NTAB*0/, iy /0/
      if (idum.le.0.or.iy.eq.0) then
        idum=max(-idum,1)
        do 11 j=NTAB+8,1,-1
          k=idum/IQ
          idum=IA*(idum-k*IQ)-IR*k
          if (idum.lt.0) idum=idum+IM
          if (j.le.NTAB) iv(j)=idum
11      continue
        iy=iv(1)
      endif
      k=idum/IQ
      idum=IA*(idum-k*IQ)-IR*k
      if (idum.lt.0) idum=idum+IM

      j=1+iy/NDIV
      iy=iv(j)
      iv(j)=idum
      ran1=min(AM*iy,RNMX)
      return
      END
