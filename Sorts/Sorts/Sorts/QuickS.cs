using System;
using System.Collections.Generic;
using System.Text;

namespace Sorts.Sorts
{
    public class QuickS : SortSolution
    {
        public QuickS(string path) : base(path)
        {
        }

        int Partition(List<IComparable> arr, int low, int high)
        {
            IComparable pivot = arr[low];

            int i = low - 1;
            int j = high + 1;

            while (true)
            {
                do
                {
                    i++;
                } while (arr[i].CompareTo(pivot) == -1);
                do
                {
                    j--;
                } while (arr[j].CompareTo(pivot) == 1);

                if (i >= j)
                    return j;
                Swap(arr, i, j);
            }
        }

        void Swap(List<IComparable> arr, int i, int j)
        {
            IComparable temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }


        void QuickSort(List<IComparable> arr, int low, int high, CancellationToken token)
        {
            token.ThrowIfCancellationRequested();
            if (low >= 0 && high >= 0 && low < high)
            {
                int pi = Partition(arr, low, high);

                QuickSort(arr, low, pi, token);
                QuickSort(arr, pi + 1, high, token);
            }
        }

        public override Task Solve(CancellationToken token)
        {
            QuickSort(toCompare, 0, toCompare.Count - 1, token);
            return Task.CompletedTask;
        }
    }
}
