using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace Sorts.Sorts
{
    public abstract class SortSolution
    {
        protected List<IComparable> toCompare;
        protected int maxProgress = 1;
        protected int progress = 1;
        public DateTime startTime;
        string inputPath;

        public SortSolution(string _inputPath)
        {
            InputParserGroup inputParserGroup = new InputParserGroup(_inputPath);
            toCompare = inputParserGroup.ParseInput();
            inputPath = $"{_inputPath} {GetType().Name}";
        }
        public abstract Task Solve(CancellationToken token);
        public async Task WriteOutput()
        {
            await using StreamWriter writer = new StreamWriter($"Sorts/{inputPath}.txt");
            await writer.WriteAsync($"Progress: {((progress / (float)maxProgress) * 100):0.###}%\n");
            await writer.WriteAsync($"Time spend: {(DateTime.Now - startTime).Minutes} m, {(DateTime.Now - startTime).Seconds} s, {(DateTime.Now - startTime).Milliseconds} ms \n");
            await writer.WriteAsync(String.Join('\n', toCompare));
        }
    }
}
