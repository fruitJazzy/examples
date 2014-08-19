#include <iostream>
#include <vector>
using namespace std;



int main(){
	int size = 10;

	vector<int>box(size);
	int sum=0;
	for (int i = 0; i < size-1; ++i){
		box[i] = i + 1;
		if (box[i] % 3 == 0 || box[i] % 5 == 0){
			sum += box[i];
			cout << box[i] << ' ';
		}
	}
	cout << '\n'<<sum;
	
	return 0;
}