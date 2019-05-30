#include <iostream>
#include <random>
#include <cmath>
#include <conio.h>
#include <fstream>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define LATTICE_SIZE 50
#define DELTA_PHI 180
#define TEMP_POINTS 70

double OMITED_STEPS = 30000;
double MONTE_CARLO_STEPS = 200000;
int factor = 100;
double T[TEMP_POINTS];
double Qxx=0, Qxy=0, Qyx=0, Qyy=0;

int mod(int a, int b){
    if(a%b>=0) return a%b;
    else return LATTICE_SIZE + a;
}
void printForPlot(double Qxx, double Qxy, double Qyx, double Qyy){
    std::cout<<"w,v = la.eig(np.array([["<<Qxx<<","<<Qxy<<"],["<<Qyx<<","<<Qyy<<"]]))"<<std::endl;
    std::cout<<"eigenValues.append(max(w))"<<std::endl;
}
double getRand() { return (double) rand() / (RAND_MAX); }
int calculateNewAngle(int oldAngle) { return  oldAngle + (getRand()-0.5)*DELTA_PHI; }
double calculateRadians(double degree) { return degree*(M_PI/180.0); }
double calculateProbability(double deltaEnergy, double temperature){ return exp(-deltaEnergy/temperature); }
double calculateLegendrePolynomial(double theta){
    return 0.5*(3*cos(calculateRadians(theta))*cos(calculateRadians(theta)) - 1);
}
double calculateEnergy(int angle, int arr[LATTICE_SIZE][LATTICE_SIZE], int a, int b){
    return -calculateLegendrePolynomial(angle - arr[mod(a+1, LATTICE_SIZE)][b])
                -calculateLegendrePolynomial(angle - arr[mod(a-1, LATTICE_SIZE)][b])
                -calculateLegendrePolynomial(angle - arr[a][mod(b+1, LATTICE_SIZE)])
                -calculateLegendrePolynomial(angle - arr[a][mod(b-1, LATTICE_SIZE)]);
}

void mcs(int arr[LATTICE_SIZE][LATTICE_SIZE], double temp){
    for(int i=0; i<LATTICE_SIZE; i++){
        for(int ii=0; ii<LATTICE_SIZE; ii++){
            int a = rand() % LATTICE_SIZE;
            int b = rand() % LATTICE_SIZE;
            double Ut=0, Uo=0;
            double deltaEnergy;
            int oldAngle = arr[a][b];
            int newAngle = calculateNewAngle(oldAngle);
            if(newAngle > 90) newAngle = newAngle - 180;
            else if (newAngle < -90) newAngle = newAngle + 180;
            Ut = calculateEnergy(newAngle, arr, a, b);
            Uo = calculateEnergy(oldAngle, arr, a, b);
            deltaEnergy = Ut-Uo;
            if(Ut < Uo) arr[a][b] = newAngle;
            else if (getRand() < calculateProbability(deltaEnergy, temp)) arr[a][b] = newAngle;
        }
    }
}

void updateQ(int arr[LATTICE_SIZE][LATTICE_SIZE]){
    for(int i=0; i<LATTICE_SIZE; i++){
        for(int ii=0; ii<LATTICE_SIZE; ii++){
            Qxx = Qxx + (2.0 * cos(calculateRadians(arr[i][ii]))*cos(calculateRadians(arr[i][ii])) - 1.0)/(LATTICE_SIZE*LATTICE_SIZE);
            Qxy = Qxy + (2.0 * cos(calculateRadians(arr[i][ii]))*sin(calculateRadians(arr[i][ii])))/(LATTICE_SIZE*LATTICE_SIZE);
        }
    }
    Qyy = -Qxx;
    Qyx = Qxy;
}

int main() {

    srand(time(nullptr));

    int lattice[LATTICE_SIZE][LATTICE_SIZE];
    for(int i=0; i<LATTICE_SIZE; i++) {
        for (int ii = 0; ii < LATTICE_SIZE; ii++) {
            lattice[i][ii] = round(rand() % 180 - 90);
        }
    }

    for(int i=0; i<TEMP_POINTS; i++){
        T[i]=0.025+i*0.025;
    }

    double divider = 0;
    for(int point=0; point<TEMP_POINTS; point++){
        for(int i=0; i<OMITED_STEPS; i++) mcs(lattice, T[point]);

        for (int step=0; step<MONTE_CARLO_STEPS; step++){
            mcs(lattice, T[point]);

            if(step%factor == 0){
                updateQ(lattice);
                divider++;
            }
        }
        Qxx = Qxx/divider;
        Qxy = Qxy/divider;
        Qyx = Qyx/divider;
        Qyy = Qyy/divider;

        printForPlot(Qxx, Qxy, Qyx, Qyy);

        Qxx = 0;
        Qxy = 0;
        Qyx = 0;
        Qyy = 0;
        divider = 0;
    }
    return 0;
}