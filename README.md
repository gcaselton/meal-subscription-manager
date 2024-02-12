# Meal Subscription Manager

This Java program helps you manage subscribers and their meal subscriptions efficiently. It's designed to handle subscriptions, add and remove meals, and generate letters for unfulfilled requests. Below is a breakdown of the program's key features and instructions for usage.

## Classes Overview:

1. **MainProgram Class:**
   - The driver class for the program.
   - Manages subscribers, meals, and their subscriptions.
   - Allows users to interact with the program through a console-based menu.

2. **Meal Class:**
   - Represents a meal with fields for name and quantity available.
   - Implements Comparable to enable sorting by meal name.

3. **Subscriber Class:**
   - Represents a subscriber with first name, surname, and a list of subscriptions.
   - Implements Comparable to enable sorting by surname and first name.

4. **SortedLinkedList Class:**
   - Extends LinkedList and includes an Insertion Sort algorithm.
   - Used to store and order subscribers and meals lexicographically.

5. **Subscription Class:**
   - Stores information about specific meals that subscribers are subscribed to.
   - Similar structure and methods to the Meal class.

## Program Features:

- **Subscriber and Meal Management:**
  - Subscribers and meals are sorted lexicographically for easy retrieval.
  - Adding, removing, and displaying subscribers and meals.

- **Subscription Handling:**
  - Subscribers can have up to three subscriptions.
  - Adding and removing meals from a subscriber's subscriptions.

- **Letter Generation:**
  - Generates letters for subscribers when there are not enough meals to fulfill a request.

## How to Use:

1. **Compile and Run:**
   - Compile and run the `MainProgram` class.
   - Follow the on-screen instructions to interact with the menu.

2. **Menu Options:**
   - **'m':** Display information about all the meals.
   - **'s':** Display information about all the subscribers.
   - **'a':** Add meals to a subscriber's subscription.
   - **'r':** Remove meals from a subscriber's subscription.
   - **'f':** Finish and exit the program.

3. **Input File:**
   - The program reads subscriber and meal information from the 'input_data.txt' file.
   - Ensure the file structure matches the provided example.

Feel free to explore, modify, and enhance the code as needed.
