using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace Sorts.Parsers
{
    public class BaseParser
    {
        public void Parse(StreamReader reader, List<IComparable> list)
        {
            while (reader.EndOfStream)
            {
                list.Add(ParseInput(reader.ReadLine()));
            }
        }

        protected virtual IComparable ParseInput(string line) => line;
        
    }
}
