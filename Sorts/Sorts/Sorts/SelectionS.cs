using System;
using System.Collections.Generic;
using System.Text;

namespace Sorts.Sorts
{
    internal class SelectionS : SortSolution
    {
        public SelectionS(string inputPath) : base(inputPath)
        {
        }
        public override Task Solve(CancellationToken token)
        {
            maxProgress = toCompare.Count;
            for (progress = 0; progress < maxProgress; progress++)
            {
                for(int j = progress + 1; j < maxProgress; j++)
                {
                    if(toCompare[j].CompareTo(toCompare[progress]) < 0)
                    {
                        IComparable temp = toCompare[progress];
                        toCompare[progress] = toCompare[j];
                        toCompare[j] = temp;
                    }
                }
                token.ThrowIfCancellationRequested();
            }
            return Task.CompletedTask;
        }
    }
}
