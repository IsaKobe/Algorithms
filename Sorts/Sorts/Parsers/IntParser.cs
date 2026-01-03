using System;
using System.Collections.Generic;
using System.Text;

namespace Sorts.Parsers
{
    public class IntParser : BaseParser
    {
        protected override IComparable ParseInput(string line)
            => int.Parse(line);
    }
}
