using System;
using System.Collections.Generic;
using Lab4;

namespace Lab4
{
    internal static class Program
    {
        private static readonly List<string> hosts = new List<string> {
            "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/index.html",
            "www.google.com",
            "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/lab-4-futures-continuations.html"
        };

        static void Main(string[] args)
        {
            // Directly implement the parser on the callbacks (event-driven)
            Callback.Run(hosts);
            Console.WriteLine("\n\n");

            // Wrap the connect/send/receive operations in tasks,
            // with the callback setting the result of the task
            //TaskMechanism.Run(hosts);
            //Console.WriteLine("\n\n");

            // Like the previous, but also use the async/await mechanism
            //Async.Run(hosts);
            //Console.WriteLine("\n\n");
        }
    }
}