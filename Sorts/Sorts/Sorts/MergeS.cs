using System;
using System.Collections.Generic;
using System.Text;

namespace Sorts.Sorts
{
    public class MergeS : SortSolution
    {
        public MergeS(string inputPath) : base(inputPath)
        {
        }

        void Merge(List<IComparable> comparables, int begining, int middle, int end)
        {
            int n1 = middle - begining + 1;
            int n2 = end - middle;

            IComparable[] L = new IComparable[n1];
            IComparable[] R = new IComparable[n2];
            int i, j;

            for (i = 0; i < n1; ++i)
                L[i] = comparables[begining + i];
            for (j = 0; j < n2; ++j)
                R[j] = comparables[middle + 1 + j];

            i = 0;
            j = 0;

            int k = begining;
            while (i < n1 && j < n2)
            {
                if (L[i].CompareTo(R[j]) == -1)
                {
                    comparables[k] = L[i];
                    i++;
                }
                else
                {
                    comparables[k] = R[j];
                    j++;
                }
                k++;
            }

            while (i < n1)
            {
                comparables[k] = L[i];
                i++;
                k++;
            }

            while (j < n2)
            {
                comparables[k] = R[j];
                j++;
                k++;
            }
        }

        void Divide(List<IComparable> comparables, int begining, int end, CancellationToken token)
        {
            token.ThrowIfCancellationRequested();
            if (begining != end) 
            {
                int middle = begining + (end - begining) / 2;

                Divide(comparables, begining, middle, token);
                Divide(comparables, middle + 1, end, token);

                Merge(comparables, begining, middle, end);
            }
        }

        public override Task Solve(CancellationToken token)
        {
            Divide(toCompare, 0, toCompare.Count - 1, token);
            return Task.CompletedTask;
        }
    }
}
