using System;
using System.Collections.Generic;
using System.Text;

namespace Sorts.Sorts
{
    public class InsertionS : SortSolution
    {
        public InsertionS(string inputPath) : base(inputPath)
        {
        }

        public override Task Solve(CancellationToken token)
        {
            List<IComparable> sortedList = new List<IComparable>() { toCompare[0] };

            maxProgress = toCompare.Count;
            for (progress = 1; progress < maxProgress; progress++)
            {
                IComparable comparable = toCompare[progress];
                bool ok = false;
                for (int j = 0; j < sortedList.Count; j++)
                {
                    if (sortedList[j].CompareTo(comparable) == 1)
                    {
                        sortedList.Insert(j, comparable);
                        ok = true;
                        break;
                    }
                }
                if (ok == false)
                    sortedList.Add(comparable);
                token.ThrowIfCancellationRequested();
            }
            toCompare = sortedList;
            return Task.CompletedTask;
        }
    }
}
