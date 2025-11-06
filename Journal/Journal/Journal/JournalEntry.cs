using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Journal.Journal
{
    public class JournalEntry
    {
        public DateTime entryDate;
        public string content;

        public JournalEntry(DateTime _entryDate, string _content)
        {
            entryDate = _entryDate;
            content = _content;
        }

        public override string ToString()
        {
            return $"{entryDate.ToShortDateString()}: {content.Split(' ', '\n').FirstOrDefault()}";
        }
    }
}
