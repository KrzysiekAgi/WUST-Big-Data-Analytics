#include <iostream>
#include <random>
#include <cmath>
#include <conio.h>
#include <fstream>
#include <stdlib.h>
#include <time.h>

#define LATTICE_SIZE 100
#define TEMP_POINTS 1

// Measurements
double E[TEMP_POINTS] = {0};
double M[TEMP_POINTS] = {0};
double C[TEMP_POINTS] = {0};
double X[TEMP_POINTS] = {0};
double U[TEMP_POINTS] = {0};

//Steps
double OMITTED_STEPS = 30000;
double MONTE_CARLO_STEPS = 200000;

int mod(int a, int b){
    if(a%b>=0) return a%b;
    else return LATTICE_SIZE + a;
}

void mcStep(double arr[LATTICE_SIZE][LATTICE_SIZE], double beta){
    //std::mt19937 generator (123);
    //std::uniform_real_distribution<double> dis(0.0, 1.0);
    //std::uniform_real_distribution<double> indexed(0.0,LATTICE_SIZE);
        for(int i=0; i<LATTICE_SIZE; i++){
            for(int ii=0; ii<LATTICE_SIZE; ii++){
                //int a = int(indexed(generator));
                //int b = int(indexed(generator));
                int a = rand() % LATTICE_SIZE;
                int b = rand() % LATTICE_SIZE;
                double s = arr[a][b];
                double nb = arr[mod(a+1, LATTICE_SIZE)][b]
                            + arr[a][mod(b+1, LATTICE_SIZE)]
                            + arr[mod(a-1, LATTICE_SIZE)][b]
                            + arr[a][mod(b-1, LATTICE_SIZE)];
                double cost = 2*s*nb;
                double r = (((double) rand() / (RAND_MAX)));
                if (cost<0) s *= -1;
                else if ( r < exp(-cost*beta)) s*= -1;
                arr[a][b] = s;
            }
        }
}

void printLattice(double arr[LATTICE_SIZE][LATTICE_SIZE]){
    for(int i=0; i<LATTICE_SIZE; i++){
        for(int ii=0; ii<LATTICE_SIZE; ii++){
            if(arr[i][ii]==1) std::cout<<" "<<arr[i][ii]<<" ";
            else std::cout<<arr[i][ii]<<" ";
        }
        std::cout<<std::endl;
    }
}

void printLatticeForBehaviourPlot(double arr[LATTICE_SIZE][LATTICE_SIZE]){
    std::fstream lat;
    lat.open("lattice.txt", std::ios_base::app);
    lat<<"[";
    for(int i=0; i<LATTICE_SIZE; i++){
        lat<<"[";
        for(int ii=0; ii<LATTICE_SIZE; ii++){
            lat<<arr[i][ii]<<",";
        }
        lat<<"],";
    }
    lat<<"]";
    lat<<"\n\n\n";
    lat.close();
}

double calculateEnergy(double arr[LATTICE_SIZE][LATTICE_SIZE]){
    double energy = 0;
    for(int i=0; i<LATTICE_SIZE; i++){
        for(int ii=0; ii<LATTICE_SIZE; ii++){
            double s = arr[i][ii];
            double nb = arr[mod(i+1, LATTICE_SIZE)][ii]
                        + arr[i][mod(ii+1, LATTICE_SIZE)]
                        + arr[mod(i-1, LATTICE_SIZE)][ii]
                        + arr[i][mod(ii-1, LATTICE_SIZE)];
            energy += -s*nb;
        }
    }
    return energy/2;
}

double calculateMagnetisation(double arr[LATTICE_SIZE][LATTICE_SIZE]){
    double sum=0;
    for(int i=0; i<LATTICE_SIZE; i++){
        for(int ii=0; ii<LATTICE_SIZE; ii++){
            sum += arr[i][ii];
        }
    }
    return sum;
}

void writeToFile(){
    // Prepare files for writing
    std::fstream energy;
    energy.open("energy100.txt");
    std::fstream magnetisation;
    magnetisation.open("mag_tight100.txt");
    std::fstream capacity;
    capacity.open("capacity100.txt");
    std::fstream sus;
    sus.open("sus_tight100.txt");
    std::fstream binder;
    binder.open("binder100.txt");

    // Write
    for(int i=0; i<TEMP_POINTS; i++){
        //energy << E[i] << ",";
        magnetisation << M[i] << ",";
        //capacity << C[i] << ",";
        sus << X[i] << ",";
       // binder << U[i] << ",";
    }

    // Close files
    energy.close();
    magnetisation.close();
    capacity.close();
    sus.close();
    binder.close();
}

void countSpins(double arr[LATTICE_SIZE][LATTICE_SIZE]){
    std::fstream m;
    m.open("spins.txt");

    double M11 =0;
    double mag = 0;
    double tab[2000] = {0};
    int position = 0;
    for(int i=0; i<OMITTED_STEPS; i++) mcStep(arr, 1.9);
    for(int i=0; i<1; i++){
        for(int ii=0; ii<MONTE_CARLO_STEPS; ii++) {
            mcStep(arr, 1.9);
            if(ii % 1000 == 0){
                mag = calculateMagnetisation(arr);
                M11 =  mag/(LATTICE_SIZE*LATTICE_SIZE);
                tab[position] = M11;
                position++;
            }
        }
        for(int ii=0; ii<2000; ii++) m<<tab[ii]<<",";
        std::cout<<i<<std::endl;
    }

    m.close();
}

void testModel(double lattice[LATTICE_SIZE][LATTICE_SIZE]){
    // Prepare time series
    double T[TEMP_POINTS];
    for(int i=0; i<TEMP_POINTS; i++){
        T[i] = 2 + i*0.05;
    }

    // Prepare helper variables
    double ene =0, mag=0, E1=0, E2=0, M1=0, M2=0, M4=0, n1=0, n2=0, n4=0, iT=0, iT2=0;
    n1 = 1/((MONTE_CARLO_STEPS/1000) * LATTICE_SIZE * LATTICE_SIZE);
    n2 = 1/((MONTE_CARLO_STEPS/1000) * (MONTE_CARLO_STEPS/1000) * LATTICE_SIZE * LATTICE_SIZE);
    n4 = 1/(MONTE_CARLO_STEPS/1000);

    // Start model
    for(int point=0; point<TEMP_POINTS; point++){
        E1=0;E2=0;M1=0;M2=0;M4=0;

        iT = 1/T[point];
        //iT = 1/2.6;
        iT2 = iT*iT;

        // Omit steps
        for(int i=0; i<OMITTED_STEPS; i++) mcStep(lattice, iT);

        // "Real" calculation
        for(int ii=0; ii<MONTE_CARLO_STEPS; ii++){
            mcStep(lattice, iT);

            // Take every 1000th configuration
            if(ii%1000 == 0){
                ene = calculateEnergy(lattice);
                mag = calculateMagnetisation(lattice);

                E1 += ene;
                E2 += ene*ene;
                M1 += mag;
                M2 += mag*mag;
                M4 += mag*mag*mag*mag;
            }
        }

        E[point] = n1 * E1;
        M[point] = abs(n1 * M1);
        C[point] = (n1*E2 - n2*E1*E1)*iT2;
        X[point] = (n1*M2 - n2*M1*M1)*iT;
        U[point] = 1 - n4*M4/(3*M2*M2*n4*n4);
        std::cout<<"Temp point: "<<point<<std::endl;
    }

    writeToFile();
}

//TODO Finite size scaling
//TODO plot behaviour
int main(){

    srand(time(NULL));

    std::mt19937 generator (123);
    std::uniform_real_distribution<double> dis(0.0, 1.0);
    std::uniform_real_distribution<double> indexed(0.0,LATTICE_SIZE);
    // Fill the lattice
    double lattice[LATTICE_SIZE][LATTICE_SIZE];
    for(int i=0; i<LATTICE_SIZE; i++) {
        for (int ii = 0; ii < LATTICE_SIZE; ii++) {
            lattice[i][ii] = round(dis(generator));
            if (lattice[i][ii] == 0) lattice[i][ii] = -1;
        }
    }

    printLattice(lattice);
    std::cout<<std::endl<<std::endl;

    printLatticeForBehaviourPlot(lattice);

    //double T_star = 1/1;
    //double T_star = 1/2.26;
    double T_star = 1/5;

    for(int i=0; i<1050; i++) {
        mcStep(lattice, T_star);

        if(i==1 || i==4 || i==32 || i==128 || i==1024) printLatticeForBehaviourPlot(lattice);
    }

    printLattice(lattice);
    std::cout<<std::endl<<std::endl;



    //countSpins(lattice);

    //testModel(lattice);

}
