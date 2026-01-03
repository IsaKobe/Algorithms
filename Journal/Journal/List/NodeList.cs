using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace Journal.List
{
    public class NodeList<T>
    {
        public Node<T> head;
        public Node<T> tail;
        public Node<T> currentNode;

        public int count = 0;

        public override string ToString()
        {
            StringBuilder builder = new StringBuilder();
            Node<T> node = head;
            if (node == null)
                return "No nodes";
            do
            {
                if (node == currentNode)
                    builder.Append($"({node}),");
                else
                    builder.Append($"{node},");
                node = node.next;
            } while (node != null && node != head);
            return builder.ToString();
        }

        #region Public
        #region Add
        public void AddAfter(Node<T> node)
        {
            if(currentNode == null)
            {
                AddFirst(node);
            }
            else
            {
                AddA(currentNode, node);
            }
        }

        public void AddBefore(Node<T> node)
        {
            if (currentNode == null)
            {
                AddFirst(node);
            }
            else
            {
                AddB(currentNode, node);
            }
        }
        

        public void AddFirst(Node<T> node)
        {
            if (head == null || tail == null)
            {
                Init(node);
            }
            else
            {
                AddB(head, node);
            }
        }

        public void AddLast(Node<T> node)
        {
            if (head == null || tail == null)
            {
                Init(node);
            }
            else
            {
                AddA(tail, node);
            }
        }
        #endregion

        #region Removal
        public void Remove()
        {
            Remove(currentNode);
        }

        public void RemoveFirst()
        {
            Remove(head);
        }

        public void RemoveLast()
        {
            Remove(tail);
        }
        #endregion

        #region Moving
        public void Next()
        {
            if (currentNode != null)
                currentNode = currentNode.next;
            else
            {
                Console.WriteLine("No nodes!");
            }
        }

        public void Previous()
        {
            if (currentNode != null)
                currentNode = currentNode.prev;
            else
            {
                Console.WriteLine("No nodes!");
            }
        }

        public void Head()
        {
            currentNode = head;
        }

        public void Tail()
        {
            currentNode = tail;
        }
        #endregion
        #endregion

        #region Private
        #region Adding
        void AddA(Node<T> origin, Node<T> node)
        {
            node.next = origin.next;
            node.prev = origin;
            origin.next.prev = node;
            origin.next = node;
            if (origin == tail)
                tail = node;
            currentNode = node;
            count++;
        }

        void AddB(Node<T> origin, Node<T> node)
        {
            node.next = origin;
            node.prev = origin.prev;
            origin.prev.next = node;
            origin.prev = node;
            if (origin == head)
                head = node;
            currentNode = node;
            count++;
        }
        #endregion

        void Init(Node<T> node)
        {
            head = node;
            currentNode = node;
            tail = node;

            node.next = node;
            node.prev = node;
            count++;
        }


        void Remove(Node<T> node)
        {
            if (node != null)
            {
                count--;
                if (node == head)
                {
                    if (node.next != node)
                        head = node.next;
                    else
                    {
                        head = null;
                        tail = null;
                        node = null;
                        return;
                    }
                }
                else if (node == tail)
                {
                    if (node.prev != tail)
                        tail = node.next;
                    else
                    {
                        head = null;
                        tail = null;
                        node = null;
                        return;
                    }
                }
                if (node == currentNode)
                {
                    currentNode = node.next;
                }
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            else
            {
                Console.WriteLine("Cannot delete null");
            }
        }
        #endregion
    }
}
