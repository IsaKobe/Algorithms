using System;
using System.Collections.Generic;
using System.Text;

namespace Sorts
{
    public class InputParserGroup
    {

        string path;
        public InputParserGroup(string path)
        {
            this.path = path;
        }

        public List<IComparable> ParseInput()
        {
            List<IComparable> toCompare;
            StreamReader reader = new StreamReader($"Sort/{path}");
            string line = reader.ReadLine();
            
            if (int.TryParse(line, out int start))
                toCompare = ParseInts(start, reader);
            else if (double.TryParse(line, out double dstart))
                toCompare = ParseDoubles(dstart, reader);
            else
                toCompare = ParseStrings(line, reader);
            reader.Close();
            return toCompare;
        }

        List<IComparable> ParseInts(int start, StreamReader reader)
        {
            List<IComparable> toCompare = new() { start };
            string input;
            while (!reader.EndOfStream)
            {
                input = reader.ReadLine();
                toCompare.Add(int.Parse(input));
            }
            return toCompare;
        }

        List<IComparable> ParseDoubles(double start, StreamReader reader)
        {
            List<IComparable> toCompare = new() { start };
            string input;
            while ((input = reader.ReadLine()) != " ")
            {
                toCompare.Add(double.Parse(input));
            }
            return toCompare;
        }

        List<IComparable> ParseStrings(string start, StreamReader reader)
        {
            List<IComparable> toCompare = new() { start };
            string input;
            while ((input = reader.ReadLine()) != null)
            {
                toCompare.Add(input.ToLower());
            }
            return toCompare;
        }
    }
}
