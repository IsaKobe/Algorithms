using Journal.Journal;
using Journal.List;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Journal
{
    public class Program
    {
        static void Main(string[] args)
        {
            ConsoleListener listener = new();
            listener.Start();
            /*
            NodeList<string> nodeList = new NodeList<string>();
            nodeList.AddBefore(new Node<string>("a"));
            nodeList.AddFirst(new Node<string>("x"));
            nodeList.AddAfter(new Node<string>("b"));
            nodeList.AddBefore(new Node<string>("c"));
            nodeList.AddAfter(new Node<string>("d"));
            nodeList.AddLast(new Node<string>("e"));
            Console.WriteLine(nodeList.ToString());
            nodeList.RemoveLast();
            Console.WriteLine(nodeList.ToString());
            nodeList.AddAfter(new Node<string>("y"));
            Console.WriteLine(nodeList.ToString());
            nodeList.Remove();
            Console.WriteLine(nodeList.ToString());
            nodeList.AddAfter(new Node<string>("/"));
            nodeList.Next();
            nodeList.Next();
            Console.WriteLine(nodeList.ToString());
            nodeList.Previous();
            Console.WriteLine(nodeList.ToString());*/
        }
    }
}
