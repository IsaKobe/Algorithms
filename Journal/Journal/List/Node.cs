using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Journal.List
{
    public class Node<T>
    {
        public Node<T> next;
        public Node<T> prev;
        public T value;

        public Node(T data)
        {
            value = data;
        }

        public override string ToString()
        {
            return value.ToString();
        }
    }
}
