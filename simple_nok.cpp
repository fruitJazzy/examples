#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int main(int argc, char** argv){
	int number;
	cin >> number;

	vector<int>nok(number / 2 + 1);
	int count = 0;

	for (int i = 2; i < number / 2 + 1; ++i){
		if (number%i == 0){
			nok[count] = i;
			++count;
		}
	}

	for (int i = 0; i < nok.size(); ++i){
		for (int j = 2; j < nok[i]; ++j){
			if (nok[i] % j == 0)
				nok[i] = 0;
		}
	}

	int max = nok[0];

	for (int i = 1; i < nok.size(); ++i){
		if (max < nok[i])
			max = nok[i];
	}
	cout << max;
	return 0;
}