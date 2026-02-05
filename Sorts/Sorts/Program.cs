using Sorts.Sorts;

namespace Sorts
{
    internal class Program
    {
        static async Task Main(string[] args)
        {
            //string path = "random_words_10M.txt";
            string path = "rando_1M_cela_cisla.txt";
            //string path = "test.txt";
            List<SortSolution> sorts = new() 
            {
                /*new SelectionS(path),*/
                /*new BubleS(path),*/
                /*new InsertionS(path),*/
                new HeapS(path),
                /*new MergeS(path),
                new QuickS(path),
                new RadixS(path)*/
            };
            await SolveAll(sorts);
        }

        static async Task SolveAll(List<SortSolution> sorts)
        {
            using var cts = new CancellationTokenSource(TimeSpan.FromMinutes(15));

            try
            {
                var tasks = sorts.Select(q => Task.Run(async () =>
                {
                    try
                    {
                        q.startTime = DateTime.Now;
                        await q.Solve(cts.Token);
                        await q.WriteOutput();
                    }
                    catch(OperationCanceledException)
                    {
                        await q.WriteOutput();
                    }
                    catch(Exception ex)
                    {
                        await q.WriteOutput();
                        Console.WriteLine($"An error occurred in {q.GetType().Name}: {ex.Message}");
                    }
                }, cts.Token));

                await Task.WhenAll(tasks);
            }
            catch (Exception)
            {
                Console.WriteLine("Sorting timed out and operations were cancelled.");
            }
        }
    }
}
