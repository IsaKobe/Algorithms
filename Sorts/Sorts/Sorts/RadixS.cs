using System;
using System.Collections.Generic;
using System.Text;

namespace Sorts.Sorts
{
    public class RadixS : SortSolution
    {

        public RadixS(string inputPath) : base(inputPath)
        {
        }

        void AddItemToBucket(char c, string s, List<string>[] comparables)
        {
            List<string> list = comparables[c];
            if (list == null) 
            {
                list = new List<string>();
                comparables[c] = list;
            }
            list.Add(s);
        }

        public override Task Solve(CancellationToken token)
        {
            IEnumerable<string> strings = toCompare.Select(q => q.ToString());
            
            int length = strings.Max(q => q.Length);
            strings = strings.Select(q => q.PadLeft(length, '0'));

            List<string>[] comparables = new List<string>[123];
            int i = length - 1;
            foreach (string s in strings)
            {
                AddItemToBucket(s[length-1], s, comparables);
            }
            maxProgress = length;
            for (i = length - 2; i > -1; i--) 
            {
                List<string>[] comparablesTemp = new List<string>[123];
                foreach (var strList in comparables)
                {
                    if (strList == null)
                        continue;
                    foreach (var str in strList)
                    {
                        AddItemToBucket(str[i], str, comparablesTemp);
                    }
                }
                comparables = comparablesTemp;
                progress++;
                token.ThrowIfCancellationRequested();
            }
            List<IComparable> result = new();
            foreach (var strList in comparables)
            {
                if (strList == null)
                    continue;
                foreach (var str in strList)
                {
                    result.Add(str);
                }
            }

            toCompare = result;
            return Task.CompletedTask;
        }
    }
}
