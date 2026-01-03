using System;
using System.Collections.Generic;
using System.Text;

namespace Sorts.Sorts
{
    public class BubleS : SortSolution
    {
        public BubleS(string inputPath) : base(inputPath)
        {
        }
        public override Task Solve(CancellationToken token)
        {
            IComparable placeholder;
            maxProgress = toCompare.Count;
            for (int i = toCompare.Count - 1; i > -1; i--)
            {
                for (int j = 0; j < i; j++)
                {
                    if (toCompare[j].CompareTo(toCompare[j + 1]) > 0)
                    {
                        placeholder = toCompare[j + 1];
                        toCompare[j + 1] = toCompare[j];
                        toCompare[j] = placeholder;
                    }
                }
                progress++;
                token.ThrowIfCancellationRequested();
            }
            return Task.CompletedTask;
        }
    }
}
