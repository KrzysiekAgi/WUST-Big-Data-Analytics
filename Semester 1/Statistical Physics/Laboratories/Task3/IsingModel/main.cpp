#include <iostream>
#include <random>
#include <cmath>
#include <conio.h>
#include <fstream>
#include <stdlib.h>
#include <time.h>

#define LATTICE_SIZE 100
#define TEMP_POINTS 34

// Measurements
double E[TEMP_POINTS] = {0};
double M[TEMP_POINTS] = {0};
double C[TEMP_POINTS] = {0};
double X[TEMP_POINTS] = {0};
double U[TEMP_POINTS] = {0};

//Steps
double OMITTED_STEPS = 30000;
int MONTE_CARLO_STEPS = 200000;
int factor = 100;

int mod(int a, int b){
    if(a%b>=0) return a%b;
    else return LATTICE_SIZE + a;
}

void mcStep(double arr[LATTICE_SIZE][LATTICE_SIZE], double beta){
        for(int i=0; i<LATTICE_SIZE; i++){
            for(int ii=0; ii<LATTICE_SIZE; ii++){
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
    //Two pairs and size
    return energy/(2*((LATTICE_SIZE*LATTICE_SIZE)));
}

double calculateMagnetisation(double arr[LATTICE_SIZE][LATTICE_SIZE]){
    //average spin
    double sum=0;
    for(int i=0; i<LATTICE_SIZE; i++){
        for(int ii=0; ii<LATTICE_SIZE; ii++){
            sum += arr[i][ii];
        }
    }
    return sum/(LATTICE_SIZE*LATTICE_SIZE);
}

// @param temperature is actually 1/T*
double calculateSusceptibility(double power2Magnetisation, double meanMagnetisation, double temperature){
    return ((LATTICE_SIZE*LATTICE_SIZE)*temperature)*(power2Magnetisation-meanMagnetisation*meanMagnetisation);
}

// @param temperature is actually 1/T*
double calculateSpecificHeat(double power2Energy, double meanEnergy, double temperature){
    return (LATTICE_SIZE*LATTICE_SIZE)/(temperature*temperature)*(power2Energy-meanEnergy*meanEnergy);
}

double calculateBindersCumulant(double power4Spins, double power2Spins) {
    return 1 - (power4Spins/(3*power2Spins*power2Spins));
}

// Calculate M (big M) for Binders Cumulant
double sumSpins(double arr[LATTICE_SIZE][LATTICE_SIZE]){
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
    magnetisation.open("magnetisation100.txt");
    std::fstream capacity;
    capacity.open("capacity100.txt");
    std::fstream sus;
    sus.open("sus100.txt");
    std::fstream binder;
    binder.open("binder100.txt");

    // Write
    for(int i=0; i<TEMP_POINTS; i++){
        energy << E[i] << ",";
        magnetisation << M[i] << ",";
        capacity << C[i] << ",";
        sus << X[i] << ",";
        binder << U[i] << ",";
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
    double T[TEMP_POINTS] = {1.2,1.35, 1.5, 1.65, 1.79, 1.94, 2., 2.03571429, 2.07142857, 2.10714286, 2.14285714,
                       2.17857143, 2.21428571, 2.25, 2.269, 2.28571429, 2.32142857, 2.35714286, 2.39285714,
                       2.42857143, 2.46428571, 2.5, 2.55, 2.67, 2.78, 2.89, 3., 3.12, 3.26, 3.41, 3.55, 3.7, 3.85, 4.};

    // Prepare helper variables
    double iT=0, mag=0, ene=0, sp=0;
    double meanEnergy=0, meanMagnetisation=0, susceptibility=0, specificHeat=0, bindersCumulant=0;
    double power2Energy=0, power2Magnetisation=0, power2Spins=0, power4Spins=0;

    const clock_t begin_time = clock();

    // Start model
    for(int point=0; point<TEMP_POINTS; point++){
        meanMagnetisation = 0;
        meanEnergy = 0;
        power2Energy = 0;
        power2Magnetisation = 0;
        power2Spins=0;
        power4Spins=0;
        susceptibility=0;
        specificHeat=0;
        bindersCumulant=0;

        iT = 1/T[point];

        // Omit steps
        for(int i=0; i<OMITTED_STEPS; i++) mcStep(lattice, iT);

        // "Real" calculation
        for(int ii=0; ii<MONTE_CARLO_STEPS; ii++){
            mcStep(lattice, iT);

            // Take every 1000th configuration
            if(ii%factor == 0){
                mag = calculateMagnetisation(lattice);
                ene = calculateEnergy(lattice);
                sp = sumSpins(lattice);
                meanMagnetisation += abs(mag);
                meanEnergy += ene;
                power2Energy += ene*ene;
                power2Magnetisation += mag*mag;
                power2Spins += sp*sp;
                power4Spins += sp*sp*sp*sp;
            }
        }


        meanMagnetisation = meanMagnetisation/(MONTE_CARLO_STEPS/factor);
        std::cout<<"Mean magnetisation for temp: "<<T[point]<<" = "<<meanMagnetisation<<std::endl;

        meanEnergy = meanEnergy/(MONTE_CARLO_STEPS/factor);
        std::cout<<"Mean energy for temp: "<<T[point]<<" = "<<meanEnergy<<std::endl;

        // Average
        power2Energy = power2Energy/(MONTE_CARLO_STEPS/factor);
        power2Magnetisation = power2Magnetisation/(MONTE_CARLO_STEPS/factor);
        power2Spins = power2Spins/(MONTE_CARLO_STEPS/factor);
        power4Spins = power4Spins/(MONTE_CARLO_STEPS/factor);

        susceptibility = calculateSusceptibility(power2Magnetisation, meanMagnetisation, iT);
        std::cout<<"Mean susceptibility for temp: "<<T[point]<<" = "<<susceptibility<<std::endl;

        specificHeat = calculateSpecificHeat(power2Energy, meanEnergy, 1/iT);
        std::cout<<"Mean specific heat for temp: "<<T[point]<<" = "<<specificHeat<<std::endl;

        bindersCumulant = calculateBindersCumulant(power4Spins, power2Spins);
        std::cout<<"Binder's Cummulant for temp: "<<T[point]<<" = "<<bindersCumulant<<std::endl<<std::endl;

        E[point] = meanEnergy;
        M[point] = meanMagnetisation;
        X[point] = susceptibility;
        C[point] = specificHeat;
        U[point] = bindersCumulant;
    }

    std::cout << std::endl<<std::endl<<std::endl<<float( clock () - begin_time ) /  CLOCKS_PER_SEC;

    writeToFile();
}

int main(){

    srand(time(nullptr));
    double r = ((double) rand() / (RAND_MAX));

    // Fill the lattice
    double lattice[LATTICE_SIZE][LATTICE_SIZE];
    for(int i=0; i<LATTICE_SIZE; i++) {
        for (int ii = 0; ii < LATTICE_SIZE; ii++) {
            lattice[i][ii] = round(r);
            if (lattice[i][ii] == 0) lattice[i][ii] = -1;
        }
    }

    countSpins(lattice);

    testModel(lattice);

    for(int i=0; i<OMITTED_STEPS; i++) mcStep(lattice, 1/5);

    printLatticeForBehaviourPlot(lattice);
}
