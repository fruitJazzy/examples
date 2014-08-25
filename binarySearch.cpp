//бинарный поиск. сравниваем искомый элемент с медианой и ищем в том отрезке которому подходит эта переменная.
int binarySearch(int *arr,int count,int element)[
	int first = 0;
	int last = count;
	
	while(first < last){
		int middle = (first + last) / 2;
		if(arr[middle] > element)
			last = middle;
		else
			first = middle + 1;
	}
	return (first == count || arr[first] != element) ? -1 : first;
}
