using System;
using System.Collections.Generic;
using System.Text;

namespace Sorts.Parsers
{
    public class DoubleParser : BaseParser
    {
        protected override IComparable ParseInput(string line)
            => double.Parse(line);
    }
}
