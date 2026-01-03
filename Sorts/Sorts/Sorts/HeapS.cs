using System;
using System.Collections.Generic;
using System.Net.Security;
using System.Text;

namespace Sorts.Sorts
{
    public class HeapS : SortSolution
    {
        public HeapS(string inputPath) : base(inputPath)
        {
        }

        static void heapify(List<IComparable> arr, int n, int i)
        {
            // Initialize largest as root
            int largest = i;

            // left index = 2*i + 1
            int l = 2 * i + 1;

            // right index = 2*i + 2
            int r = 2 * i + 2;

            // If left child is larger than root
            if (l < n && arr[l].CompareTo(arr[largest]) == 1)
                largest = l;

            // If right child is larger than largest so far
            if (r < n && arr[r].CompareTo(arr[largest]) == 1)
                largest = r;

            // If largest is not root
            if (largest != i)
            {
                IComparable temp = arr[i];
                arr[i] = arr[largest];
                arr[largest] = temp;

                // Recursively heapify the affected sub-tree
                heapify(arr, n, largest);
            }
        }

        public override Task Solve(CancellationToken token)
        {
            int n = toCompare.Count;

            for (int i = n / 2 - 1; i >= 0; i--)
                heapify(toCompare, n, i);

            maxProgress = n;
            for (int i = n - 1; i > 0; i--)
            {
                IComparable temp = toCompare[0];
                toCompare[0] = toCompare[i];
                toCompare[i] = temp;

                heapify(toCompare, i, 0);
                progress++;

                token.ThrowIfCancellationRequested();
            }
            return Task.CompletedTask;
        }
    }
}
