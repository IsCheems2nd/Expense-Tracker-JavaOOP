**VIETNAMESE – GERMAN UNIVERSITY**

FACULTY OF ENGINEERING

COMPUTER SCIENCE DEPARTMENT

 

 


JAVA PROJECT REPORT  
Expense Tracking App


***Module 61CSE215: Object Oriented Programming in JAVA***

1\.         Vương Gia Kiệt – 10423067  
2\.         Nguyễn Quốc Khang – 10423055

Lecturer: Dr. Tran Hong Ngoc

 

 

 

 VGU, WS2025

Versions

| Date | Version | In charge | Description |
| :---: | :---: | ----- | ----- |
| 25-October 24 | V1.0 | Ngoc Tran, A Nguyen | Update Section I, II and Appendix A |
|  |  |  |  |

Abbreviation

DMS	Database Management System

…	…

List of Figures

Figure 1 The accuracy between X and Y	10

List of Tables

Table 1: A comparison between X and Y	10

Table of Contents

I….	3  
II…	4  
III….	5

1. **INTRODUCTION**

*In this project, we investigate the business requirements of a personal finance management system and develop a desktop application that helps users efficiently track their daily income and expenses. The primary goal is to provide a simple, user-friendly tool that supports individuals in managing their personal finances, monitoring spending habits, and gaining financial awareness.*

*This application is particularly useful for students and staff of the **Vietnamese-German University (VGU)**, as it includes built-in **VND ↔ EUR/USD currency conversion** to accommodate budgeting in both local and international contexts. By offering a clear overview of financial transactions and monthly summaries, the system enables users to make more informed financial decisions.*

*This project provides the basic functions for the stakeholders/users as follows:*

- *Adding income and expense transactions*  
- *Categorizing transactions*  
- *Viewing transaction history*  
- *Displaying total balance, periodic summaries*  
- *Automatically converting between EUR, USD and VND*  
- *Exporting data to  PDF for personal archiving or reporting*

  *Use case diagram:*  
  *![][image1]*

*In this project, we use Java OOP for designing classes and applying the OOP such as encapsulation, inheritance, and polymorphism for designing controllers, models, and UI components. The project also ensures MVC (Model-View-Controller) structure, code reusability, and clean separation between frontend and backend logic.*

2. **CLASS ANALYSIS**  
     
1. *Objects*

| *No* | *Object Name* | *State* | *Behaviours* |
| :---- | :---- | :---- | :---- |
| *1* | *Transaction 1* | *Id: 1, amount 1000, category: “Scholarships”, description: “DAAD”, currencyCode: “EUR”*  | *getId, getAmount, getCategory, getDescription, getDateTime, getDate, getCurrencyCode,setAmount, setCategory, setDescription, setDateTime, setCurrencyCode, getFormattedDateTime, toString* |
| *2* | *Database* | *URL: "jdbc:sqlite:data/expense.db"* | *getConnection, init, insertSampleData* |
| *3* | *TransactionController* | *EXCHANGE\_RATES: Map\<String, Double\>, controller instance* | *addTransaction, deleteTransaction, updateTransaction, getAllTransactions,getCurrentBalance, filterByCategory, filterByDateRange, filterByType, getUniqueCategories, convertToBaseCurrency, getBaseCurrencyAmount* |
| *4* | *MainFrame* | *title: "Expense Tracker", width: 600, height: 800, controller: TransactionController* | *addGuiComponents, addWelcomeLabel, addBalanceLabel, addBalanceTextField, addAddTransactionButton, addBrowseHistoryButton, addViewReportButton, addGenerateReportButton, addMoneyFlowPanel, calculateBalance, actionPerformed* |
| *5* | *AddExpenseFrame* | *title: "Add Transaction", width: 600, height: 800, controller: TransactionController, amountPanel, categoryPanel, datePanel, descriptionPanel, transactionTypePanel, buttonPanel* | *addGuiComponents, addPanels, initializePanels, arrangePanels, addWelcomingComponents, getAmountPanel, getCategoryPanel, getDatePanel, getDescriptionPanel, getTransactionTypePanel* |
| *6* | *TransactionHistoryFrame* | *Title: “Transaction History”, width: 600, height: 800, controller: TransactionController, historyCardsScrollPane, historyCardsPanel, transactionHistoryButtonPanel* | *addGuiComponents, addPanels, initializePanels, arrangePanels, addWelcomingComponents, createTransactionHistoryLabel, refreshTransactionFrame* |
| *7* | *SummaryFrame* | *Title: “Summary Report”, width: 800, height: 700, controller: TransactionController, filterPanel, chartPanel, buttonPanel*  | *addGuiComponents, addWelcomingComponents, arrangePanels, initializePanels, addPanels* |

   *Table1. List of Objects (Transactions, UI Frames, Controller)*

   

2. *Classes*

| *No* | *Class Name* | *Attributes* | *Functions* |
| :---- | :---- | :---- | :---- |
| *1* | *Transaction*  | *id, amount, category, description, dateTime, date, currencyCode* | *getId, getAmount, getCategory, getDescription, getDateTime, getDate, getCurrencyCode,setAmount, setCategory, setDescription, setDateTime, setCurrencyCode, getFormattedDateTime, toString* |
| *2* | *Database* | *URL (static)* | *getConnection, init, insertSampleData* |
| *3* | *TransactionController* | *EXCHANGE\_RATES (static)* | *addTransaction, deleteTransaction, updateTransaction, getAllTransactions,getCurrentBalance, filterByCategory, filterByDateRange, filterByType, getUniqueCategories, convertToBaseCurrency, getBaseCurrencyAmount* |
| *4* | *BaseFrame (abstract class)* | *Title, controller, width, height* | *initializeComponents, getController, createSeparator, addGuiComponents (abstract)* |
| *5* | *MainFrame* | *Inherits BaseFrame* | *addGuiComponents, addWelcomeLabel, addBalanceLabel, addBalanceTextField, addAddTransactionButton, addBrowseHistoryButton, addViewReportButton, addGenerateReportButton, addMoneyFlowPanel, calculateBalance, actionPerformed* |
| *6* | *AddExpenseFrame* | *Inherit BaseFrame, amountPanel, transactionTypePanel, categoryPanel, datePanel, descriptionPanel, buttonPanel* | *addGuiComponents, addPanels, initializePanels, arrangePanels, addWelcomingComponents, getAmountPanel, getCategoryPanel, getDatePanel, getDescriptionPanel, getTransactionTypePanel* |
| *7* | *TransactionHistoryFrame* | *Inherit BaseFrame, historyCardsScrollPane, historyCardsPanel, transactionHistoryButtonPanel* | *addGuiComponents, addPanels, initializePanels, arrangePanels, addWelcomingComponents, createTransactionHistoryLabel, refreshTransactionFrame* |
| *8* | *SummaryFrame* | *Inherit BaseFrame, filterPanel, chartPanel, buttonPanel*  | *addGuiComponents, addWelcomingComponents, arrangePanels, initializePanels, addPanels* |

   

   *Inheritance diagram:*  
   *![][image2]*

   

3. **CLASS DESIGN**  
     
1. *Classes*  
- *Add a Class diagram: relationship among classes* 

The project follows a three-tier architecture (Model-View-Controller) pattern. The class relationships can be visualized as follows:

**Model Layer:**
- `Transaction` - Represents a transaction entity with attributes like id, amount, category, description, datetime, currencyCode
- `Category` - Represents a category entity (currently minimal implementation)

**Controller Layer:**
- `TransactionController` - Manages all business logic for transactions including CRUD operations, filtering, sorting, and currency conversion

**View Layer:**
- `BaseFrame` (abstract) - Base class for all GUI frames
  - `MainFrame` - Main application window
  - `AddExpenseFrame` - Form for adding transactions
  - `TransactionHistoryFrame` - Displays transaction history
  - `SummaryFrame` - Shows summary charts and reports

**Database Layer:**
- `Database` - Handles SQLite database connections and initialization

**Utility Classes:**
- `UIComponentFactory` - Factory for creating UI components
- `PDFExporter` - Exports transactions to PDF format
- `CategoryUtil` - Utility for categorizing transactions

*Figure 2\. Class Diagram of Project (See Inheritance Diagram in Section II)*

- *Add a table (provides more details)*

| *No* | *Class* | *Instance Variable* | *Methods* | *Description* |
| :---- | :---- | :---- | :---- | :---- |
| *1* | *Transaction*  | *private int id; private double amount; private String category; private String description; private LocalDateTime dateTime; private LocalDate date; private String currencyCode;* | *public int getId(); public double getAmount(); public String getCategory(); public String getDescription(); public LocalDateTime getDateTime(); public LocalDate getDate(); public String getCurrencyCode(); public String getFormattedDateTime(); public void setAmount(double amount); public void setCategory(String category); public void setDescription(String description); public void setDateTime(LocalDateTime dateTime); public void setCurrencyCode(String currencyCode); public String toString();*  | *This class represents a financial transaction with all its attributes. All fields are private for encapsulation, with public getters and setters for controlled access.* |
| *2* | *TransactionController* | *private static final Map<String, Double> EXCHANGE_RATES;* | *public void addTransaction(LocalDateTime dateTime, double amount, String category, String description, String currencyCode); public boolean deleteTransaction(int id); public boolean updateTransaction(int id, double amount, String category, String description, String currencyCode); public List<Transaction> getAllTransactions(); public Transaction findById(int id); public double getCurrentBalance(); public List<Transaction> filterByCategory(String category); public List<Transaction> filterByDateRange(LocalDate startD, LocalDate endD); public List<Transaction> filterByType(boolean isIncome); public List<Transaction> sortByDate(boolean ascending); public List<Transaction> sortByAmount(boolean ascending); public List<Transaction> search(String keyword); public Set<String> getUniqueCategories(); private double convertToBaseCurrency(double amount, String currencyCode); public double getBaseCurrencyAmount(Transaction t);* | *This class manages all transaction-related operations and business logic. It contains static exchange rates for currency conversion (USD, EUR, VND). Methods are public for external access, with private helper methods for internal use.* |
| *3* | *Database* | *private static final String URL;* | *public static Connection getConnection() throws SQLException; public static void init(); public static void insertSampleData();* | *This class manages database connectivity using SQLite. All methods are static since there's only one database connection. The URL is private static final as it should not be modified after initialization.* |
| *4* | *BaseFrame* (abstract) | *protected TransactionController controller;* | *protected abstract void addGuiComponents(); protected JSeparator createSeparator(); private void initializeComponents(String title, int width, int height);* | *This abstract class provides common frame initialization logic. The controller is protected so subclasses can access it directly. The abstract method forces subclasses to implement their specific GUI layout.* |
| *5* | *UIComponentFactory* | *None (all methods are static)* | *public static JLabel createLabel(...); public static JTextField createTextField(...); public static JButton createButton(...); public static JComboBox<String> createStringComboBox(...); public static JTextArea createTextArea(...); public static JCheckBox createCheckBox(...); public static JSeparator createSeparator(...);* | *This factory class provides static methods to create consistent UI components. All methods are static since no instance state is needed. This follows the Factory design pattern for creating UI elements with consistent styling.* |

  *Table 2\. Details of Classes*

- *For each class, design the detailed members. Students please choose the method/class/variable type for each class: public, private, default, final, static,… with explanation.*

**Access Modifier Choices:**
- **private**: Used for internal implementation details that should not be accessed directly from outside the class (e.g., fields in Transaction, helper methods)
- **protected**: Used in BaseFrame for the controller field so subclasses can access it directly without getters
- **public**: Used for methods that need to be accessed from other packages (e.g., all TransactionController business methods, UI factory methods)
- **static**: Used for methods and fields that don't require instance state (e.g., Database methods, UIComponentFactory methods, EXCHANGE_RATES map)
- **final**: Used for constants that should not be modified (e.g., Database.URL, EXCHANGE_RATES map reference)

- *Abstract classes*

  *Create a table of abstract classes*

| *No* | *Abstract Class* | *Abstract Methods* | *Concrete Methods* | *Description* |
| :---- | :---- | :---- | :---- | :---- |
| *1* | *BaseFrame* | *addGuiComponents()* | *initializeComponents(String title, int width, int height); createSeparator();* | *This abstract class extends JFrame and provides common initialization logic for all application frames. The abstract method addGuiComponents() must be implemented by subclasses (MainFrame, AddExpenseFrame, TransactionHistoryFrame, SummaryFrame) to define their specific UI layout. This follows the Template Method design pattern.* |


2. *Some OOP techniques*

*2.1.Overloading method:* 

Method overloading allows multiple methods with the same name but different parameters. In our project:

**1. LimitedDocument Constructor (Constructor Overloading)**
```java
// LimitedDocument.java
public class LimitedDocument extends PlainDocument {
    private int maxChars;
    private String charsAllowed;

    // Constructor 1: Only maxChars parameter
    public LimitedDocument(int maxChars) {
        this(maxChars, null);
    }

    // Constructor 2: Both maxChars and charsAllowed parameters
    public LimitedDocument(int maxChars, String charsAllowed) {
        this.maxChars = maxChars;
        this.charsAllowed = charsAllowed;
    }
}
```
The first constructor calls the second constructor with `null` for charsAllowed, demonstrating constructor chaining. This allows flexibility - users can create a LimitedDocument with just a character limit, or specify allowed characters.

**2. UIComponentFactory.createTextField() Method Overloading**
```java
// UIComponentFactory.java
// Overload 1: Creates text field without initial text
public static JTextField createTextField(int x, int y, int width, int height, int fontSize, boolean editable) {
    JTextField textField = new JTextField();
    textField.setBounds(x, y, width, height);
    textField.setFont(new Font("Dialog", Font.PLAIN, fontSize));
    textField.setHorizontalAlignment(SwingConstants.LEFT);
    textField.setEditable(editable);
    return textField;
}

// Overload 2: Creates text field with initial text
public static JTextField createTextField(String text, int x, int y, int width, int height, int fontSize, boolean editable) {
    JTextField textField = new JTextField(text);
    textField.setBounds(x, y, width, height);
    textField.setFont(new Font("Dialog", Font.PLAIN, fontSize));
    textField.setHorizontalAlignment(SwingConstants.LEFT);
    textField.setEditable(editable);
    return textField;
}
```
Both methods have the same name but different parameter lists. The first creates an empty text field, while the second initializes it with text. This provides flexibility when creating UI components.

*2.2. Overriding method:*

Method overriding allows subclasses to provide specific implementations of methods defined in parent classes:

**1. Transaction.toString() Method Override**
```java
// Transaction.java
@Override
public String toString(){
    return String.format("ID: %d | %s | %.2f %s | %s | %s",
                         id,
                         getFormattedDateTime(),  
                         amount,
                         currencyCode,
                         category,
                         description);
}
```
This overrides the `toString()` method from Object class to provide a formatted string representation of a Transaction object.

**2. BaseFrame.addGuiComponents() Method Override**
```java
// MainFrame.java
@Override
protected void addGuiComponents() {
    addWelcomeLabel();
    addBalanceLabel();
    addBalanceTextField();
    addAddTransactionButton();
    addBrowseHistoryButton();
    addViewReportButton();
    addGenerateReportButton();
    addMoneyFlowPanel();
}

// AddExpenseFrame.java
@Override
protected void addGuiComponents() {
    addWelcomingComponents();
    addPanels();
}
```
Both classes extend BaseFrame and override the abstract `addGuiComponents()` method with their specific implementations. Each frame has a different UI layout.

**3. LimitedDocument.insertString() Method Override**
```java
// LimitedDocument.java
@Override
public void insertString(int offset, String string, AttributeSet attributeSet) throws BadLocationException {
    if (string == null) return;
    if (string.equalsIgnoreCase("\n")) return;
    if (charsAllowed != null && !charsAllowed.isEmpty()) {
        for (char c : string.toCharArray()) {
            if (charsAllowed.indexOf(c) == -1) {
                return;
            }
        }
    }
    if (getLength() + string.length() <= maxChars) {
        super.insertString(offset, string, attributeSet);
    }
}
```
This overrides PlainDocument's `insertString()` method to add custom validation: limiting character count, filtering allowed characters, and preventing newlines.

**4. MainFrame.actionPerformed() Interface Implementation**
```java
// MainFrame.java
public class MainFrame extends BaseFrame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equalsIgnoreCase("Add Transaction")) {
            // Handle add transaction action
        } else if (cmd.equalsIgnoreCase("Browse History")) {
            // Handle browse history action
        }
        // ... more action handling
    }
}
```
This implements the `actionPerformed()` method from ActionListener interface to handle button click events.


3.  *Inheritance*  

Our project uses inheritance extensively to organize classes hierarchically:

**1. Frame Inheritance Hierarchy**
- `BaseFrame` (abstract) extends `JFrame`
  - `MainFrame` extends `BaseFrame`
  - `AddExpenseFrame` extends `BaseFrame`
  - `TransactionHistoryFrame` extends `BaseFrame`
  - `SummaryFrame` extends `BaseFrame`

All frames inherit common initialization logic from BaseFrame and implement the abstract `addGuiComponents()` method with their specific UI.

**2. Panel Inheritance**
- All panel classes extend `JPanel` (from Java Swing library):
  - `CategoryPanel extends JPanel`
  - `AmountPanel extends JPanel`
  - `DatePanel extends JPanel`
  - `DescriptionPanel extends JPanel`
  - `ChartPanel extends JPanel`
  - `FilterPanel extends JPanel`

**3. Document Filter Inheritance**
- `LimitedDocument extends PlainDocument`
  - This extends Swing's PlainDocument to add character limit and filtering functionality

**4. Interface Implementation**
- `MainFrame implements ActionListener`
  - This allows MainFrame to respond to button click events

**Representative Code:**
```java
// BaseFrame.java - Parent class
public abstract class BaseFrame extends JFrame {
    protected TransactionController controller;

    public BaseFrame(String title, TransactionController controller, int width, int height) {
        this.controller = controller;
        initializeComponents(title, width, height);
    }
    
    protected abstract void addGuiComponents(); // Force subclasses to implement
}

// MainFrame.java - Child class
public class MainFrame extends BaseFrame implements ActionListener {
    public MainFrame(TransactionController controller) {
        super("Expense Tracker", controller, 600, 800);
    }
    
    @Override
    protected void addGuiComponents() {
        // MainFrame-specific GUI implementation
        addWelcomeLabel();
        addBalanceLabel();
        // ... more components
    }
}
```

**Three-Tier Architecture:**

**Model Tier:**
- `Transaction` - Data model
- `Category` - Data model

**Controller Tier:**
- `TransactionController` - Business logic and data manipulation

**View Tier:**
- All Frame classes (MainFrame, AddExpenseFrame, etc.)
- All Panel classes
- `UIComponentFactory` - UI component creation

This separation ensures that:
- Views are independent of data storage details
- Business logic is centralized in controllers
- Models contain only data and basic operations

4. **Package Design**  

The project follows a clear package hierarchy that separates concerns:

**Package Structure:**
```
src/main/java/
├── backend/
│   ├── controller/      # Business logic layer
│   │   └── TransactionController.java
│   ├── model/          # Data models
│   │   ├── Transaction.java
│   │   └── Category.java
│   ├── db/             # Database layer
│   │   └── Database.java
│   ├── view/           # Console view (legacy)
│   │   └── ConsoleView.java
│   ├── ui/             # User interface utilities
│   │   └── TransactionUI.java
│   ├── fileExporter/   # Export functionality
│   │   └── PDFExporter.java
│   └── Main.java       # Application entry point
│
└── frontend/
    ├── app/            # Application-specific UI
    │   ├── mainFrame/  # Main window
    │   ├── addFrame/   # Add transaction window
    │   ├── transactionHistory/  # History window
    │   ├── summaryFrame/        # Summary/report window
    │   └── exportFrame/         # Export dialog
    └── components/     # Reusable UI components
        ├── UIComponentFactory.java
        ├── LimitedDocument.java
        ├── AmountDocumentFilter.java
        ├── DateDocumentFilter.java
        └── PlaceholderTextField.java
```

**Package Organization Principles:**
1. **Backend Package**: Contains all business logic, data models, and data access logic. This package has no dependency on frontend.
2. **Frontend Package**: Contains all GUI-related code and depends on backend for data operations.
3. **Separation of Concerns**: Each package has a clear responsibility:
   - `backend.controller`: Business logic
   - `backend.model`: Data entities
   - `backend.db`: Database operations
   - `frontend.app`: Application windows
   - `frontend.components`: Reusable UI components

**Package Dependencies:**
- `frontend` → `backend` (frontend uses backend services)
- `backend.controller` → `backend.model` and `backend.db`
- All frames in `frontend.app.*` → `frontend.components` and `backend.controller`

5. **Interface Design**  

Our project uses interfaces primarily through Java's built-in interfaces and one custom interface:

**1. ActionListener Interface (Java Swing)**
```java
// MainFrame.java
public class MainFrame extends BaseFrame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        // Handle button click events
    }
}
```
The `MainFrame` class implements `ActionListener` to respond to button click events. When a button is clicked, the `actionPerformed()` method is called, allowing the frame to handle different actions based on the button's action command.

**2. FilterChangeListener Interface (Custom Interface)**
```java
// FilterPanel.java
public class FilterPanel extends JPanel {
    public interface FilterChangeListener {
        void onFilterChanged(String period, String type);
    }
    
    private FilterChangeListener listener;
    
    public FilterPanel(int width, FilterChangeListener listener) {
        this.listener = listener;
        // ...
    }
    
    // When filter changes, notify listener
    combo.addItemListener(e -> {
        if (e.getStateChange() == ItemEvent.SELECTED && listener != null) {
            listener.onFilterChanged(
                (String) periodComboBox.getSelectedItem(),
                (String) typeComboBox.getSelectedItem()
            );
        }
    });
}
```

**Usage in SummaryFrame:**
```java
// SummaryFrame.java
filterPanel = new FilterPanel(getWidth(), (period, type) -> {
    chartPanel.updateChart(period, type);
});
```

This custom interface follows the Observer pattern - when filter selections change in FilterPanel, it notifies the listener (SummaryFrame), which then updates the chart. This creates loose coupling between FilterPanel and the chart update logic.

**Interface Benefits:**
- **Loose Coupling**: FilterPanel doesn't need to know about ChartPanel directly
- **Flexibility**: Any class can implement FilterChangeListener to respond to filter changes
- **Testability**: Easy to create mock implementations for testing 


6. **Access Control**  

Access control is crucial for maintaining encapsulation and proper class boundaries. Our project uses various access modifiers strategically:

**Table X1: Data Access Control Table**

| No | Data | Class | Modifier | Description |
| :---- | :---- | :---- | :---- | :---- |
| 1 | id, amount, category, description, dateTime, currencyCode | Transaction | private | These fields are private to prevent direct modification from outside the class. Access is controlled through public getters and setters, ensuring data validation and consistency. For example, setDateTime() automatically updates the date field when dateTime is set. |
| 2 | controller | BaseFrame | protected | The controller field is protected so that subclasses (MainFrame, AddExpenseFrame, etc.) can access it directly without needing a getter method, while preventing access from unrelated classes. This maintains the inheritance relationship. |
| 3 | EXCHANGE_RATES | TransactionController | private static final | This map is private to prevent external modification of exchange rates. It's static because it's shared across all instances, and final to ensure the reference cannot be reassigned (though the map contents are initialized once). |
| 4 | URL | Database | private static final | The database URL is private and final because it should never change after initialization. Making it private prevents accidental modification from outside the Database class. |
| 5 | categoryComboBox | CategoryPanel | private | The combo box is private to ensure it can only be accessed through the public getter method, maintaining encapsulation of the panel's internal components. |
| 6 | maxChars, charsAllowed | LimitedDocument | private | These fields control document behavior and should not be modified after object creation. Making them private ensures data integrity. |

**Table X2: Method Access Control Table**

| No | Method | Class | Modifier | Description |
| :---- | :---- | :---- | :---- | :---- |
| 1 | getId(), getAmount(), getCategory(), etc. | Transaction | public | All getter methods are public because Transaction objects need to expose their data to other classes like TransactionController, UI components, and exporters. These methods provide read-only access to private fields. |
| 2 | addTransaction(), deleteTransaction(), getAllTransactions() | TransactionController | public | These methods are the main API of the controller and must be accessible from the frontend package. They represent the business operations that the UI layer needs. |
| 3 | convertToBaseCurrency() | TransactionController | private | This is a helper method used internally by other methods in TransactionController. Making it private hides implementation details - external classes don't need to know about currency conversion internals, they just call getBaseCurrencyAmount(). |
| 4 | initializeComponents() | BaseFrame | private | This method handles frame initialization and should only be called from the BaseFrame constructor. Making it private prevents subclasses from calling it directly or out of order. |
| 5 | createSeparator() | BaseFrame | protected | This method is protected so subclasses can use it when implementing addGuiComponents(), but external classes cannot access it. It's a utility method for the inheritance hierarchy. |
| 6 | addGuiComponents() | BaseFrame | protected abstract | This abstract method is protected because only subclasses should implement it. Making it protected (rather than public) indicates it's part of the internal frame initialization process. |
| 7 | createTextField(), createButton(), etc. | UIComponentFactory | public static | All factory methods are public static because they need to be accessible from anywhere in the frontend package. They're static because the factory doesn't maintain any state - it's a utility class. |
| 8 | insertString() | LimitedDocument | public (override) | This overrides PlainDocument's method, so it must be public. However, it's only called by Swing's text component system, not directly by our code. The @Override annotation ensures we're actually overriding a parent method. |

**Access Control Discussion:**

The access modifiers are chosen carefully based on usage patterns:

- **Private fields** protect data integrity by preventing direct access. For example, Transaction's fields are private because we want to ensure dateTime and date stay synchronized (when dateTime changes, date must update).

- **Protected fields** in BaseFrame allow inheritance while maintaining encapsulation. The controller field is accessible to all frame subclasses but not to unrelated classes.

- **Private helper methods** like `convertToBaseCurrency()` hide implementation complexity. The public method `getBaseCurrencyAmount()` is simpler and represents the intended interface.

- **Public static methods** in utility classes like UIComponentFactory provide convenient access without requiring object instantiation.

This access control strategy ensures proper encapsulation while allowing necessary interaction between classes, following the principle of "least privilege" - classes only expose what's necessary for their intended use.


7. **Encapsulation vs Inheritance vs Polymorphism**

In this section, we discuss how the three core OOP features are applied in our Expense Tracker project, with concrete examples from our codebase.

**1. Encapsulation**

Encapsulation is the bundling of data and methods that operate on that data within a single unit (class), while hiding internal implementation details from outside access.

**Example 1: Transaction Class Encapsulation**
```java
// Transaction.java
public class Transaction {
    private int id;
    private double amount;
    private String category;
    private LocalDateTime dateTime;
    
    // Private fields with public getters/setters
    public double getAmount() {
        return amount;
    }
    
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.date = dateTime.toLocalDate(); // Automatically updates date
    }
}
```
**Why this demonstrates encapsulation:**
- All fields are private, preventing direct external modification
- Access is controlled through public getter/setter methods
- The setter method (`setDateTime()`) maintains data consistency by automatically updating the related `date` field
- External classes cannot directly modify internal state, ensuring data integrity

**Example 2: TransactionController Encapsulation**
```java
// TransactionController.java
public class TransactionController {
    private static final Map<String, Double> EXCHANGE_RATES;
    
    private double convertToBaseCurrency(double amount, String currencyCode) {
        // Private helper method - implementation hidden
        Double rate = EXCHANGE_RATES.get(currencyCode);
        if (rate == null) {
            return amount;
        }
        return amount * rate;
    }
    
    public double getBaseCurrencyAmount(Transaction t) {
        return convertToBaseCurrency(t.getAmount(), t.getCurrencyCode());
    }
}
```
**Why this demonstrates encapsulation:**
- The EXCHANGE_RATES map is private and final, preventing external modification
- The currency conversion logic is hidden in a private method
- External classes can use `getBaseCurrencyAmount()` without knowing how conversion works
- This allows us to change conversion logic without affecting code that uses the controller

**2. Inheritance**

Inheritance allows classes to inherit fields and methods from parent classes, promoting code reuse and establishing an "is-a" relationship.

**Example 1: Frame Inheritance Hierarchy**
```java
// BaseFrame.java - Parent class
public abstract class BaseFrame extends JFrame {
    protected TransactionController controller;
    
    public BaseFrame(String title, TransactionController controller, int width, int height) {
        this.controller = controller;
        initializeComponents(title, width, height);
    }
    
    protected abstract void addGuiComponents();
    
    protected JSeparator createSeparator() {
        return UIComponentFactory.createSeparator(15, 50, getWidth() - 30, 10);
    }
}

// MainFrame.java - Child class
public class MainFrame extends BaseFrame implements ActionListener {
    public MainFrame(TransactionController controller) {
        super("Expense Tracker", controller, 600, 800);
    }
    
    @Override
    protected void addGuiComponents() {
        addWelcomeLabel();
        addBalanceLabel();
        // MainFrame-specific components
    }
}
```
**Why this demonstrates inheritance:**
- All frames inherit common initialization logic from BaseFrame
- Subclasses inherit the `controller` field and `createSeparator()` method
- The abstract method forces each frame to define its own UI layout
- This eliminates code duplication - frame setup code is written once in BaseFrame
- Each frame "is-a" BaseFrame (which "is-a" JFrame), establishing a clear hierarchy

**Example 2: Panel Inheritance**
```java
// CategoryPanel.java
public class CategoryPanel extends JPanel {
    private JComboBox<String> categoryComboBox;
    
    public CategoryPanel(int width, boolean isExpense) {
        setLayout(null);
        addCategoryComponents(width, isExpense);
    }
}
```
**Why this demonstrates inheritance:**
- CategoryPanel inherits all JPanel functionality (layout management, event handling, etc.)
- We only need to add category-specific behavior
- All Swing panel capabilities are available without reimplementation

**3. Polymorphism**

Polymorphism allows objects of different classes to be treated uniformly through a common interface, enabling flexibility and extensibility.

**Example 1: Method Overriding (Runtime Polymorphism)**
```java
// BaseFrame.java
protected abstract void addGuiComponents();

// MainFrame.java
@Override
protected void addGuiComponents() {
    addWelcomeLabel();
    addBalanceLabel();
    // MainFrame implementation
}

// AddExpenseFrame.java
@Override
protected void addGuiComponents() {
    addWelcomingComponents();
    addPanels();
    // AddExpenseFrame implementation
}

// SummaryFrame.java
@Override
protected void addGuiComponents() {
    addWelcomingComponents();
    initializePanels();
    // SummaryFrame implementation
}
```
**Why this demonstrates polymorphism:**
- All three classes override the same method name with different implementations
- Code that works with BaseFrame references can call `addGuiComponents()` without knowing which specific frame type it is
- The JVM determines at runtime which implementation to execute based on the actual object type
- This allows adding new frame types (e.g., SettingsFrame) without modifying existing code that uses BaseFrame

**Example 2: Interface Polymorphism**
```java
// MainFrame implements ActionListener
public class MainFrame extends BaseFrame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        // Handle different button clicks
        if (cmd.equalsIgnoreCase("Add Transaction")) {
            new AddExpenseFrame("Add Transaction", controller, 600, 800).setVisible(true);
        }
    }
}
```
**Why this demonstrates polymorphism:**
- MainFrame can be treated as an ActionListener
- Any object implementing ActionListener can be passed to `button.addActionListener()`
- This decouples button components from specific handlers - buttons don't need to know about MainFrame specifically

**Example 3: Polymorphism through Inheritance**
```java
// All these are BaseFrame instances, but behave differently
BaseFrame mainFrame = new MainFrame(controller);
BaseFrame addFrame = new AddExpenseFrame("Add", controller, 600, 800);
BaseFrame historyFrame = new TransactionHistoryFrame("History", controller, 600, 800);

// All can call the same method, but execute different implementations
mainFrame.addGuiComponents();      // Calls MainFrame's implementation
addFrame.addGuiComponents();        // Calls AddExpenseFrame's implementation  
historyFrame.addGuiComponents();    // Calls TransactionHistoryFrame's implementation
```
**Why this demonstrates polymorphism:**
- Different objects respond to the same method call with different behaviors
- The actual implementation is determined by the object's runtime type
- This enables code that works with the base type (BaseFrame) to work with any subclass

**Summary:**

- **Encapsulation** protects data integrity by hiding implementation details (e.g., Transaction fields are private)
- **Inheritance** promotes code reuse and establishes hierarchies (e.g., all frames inherit from BaseFrame)
- **Polymorphism** enables flexible, extensible code (e.g., different frames implement addGuiComponents() differently)

These three principles work together: encapsulation protects the integrity of inherited members, inheritance establishes polymorphic hierarchies, and polymorphism allows inherited methods to behave differently in subclasses.  
     
8. **Experiment**  

1. **Environment and Tools**  

1. **Environment:** 

Development was performed on standard desktop/laptop computers with the following typical specifications:
- **CPU**: Intel Core i5 or equivalent (multi-core processor)
- **RAM**: 8GB minimum, 16GB recommended
- **Storage**: 500GB+ available space for IDE, JDK, and project files
- **Operating System**: Windows 10/11, macOS, or Linux
- **Display**: 1920x1080 resolution recommended for comfortable GUI development

2. **Tools**: 

| Tool/Library | Version | Purpose |
| :---- | :---- | :---- |
| Java Development Kit (JDK) | 21 | Core Java language and runtime environment |
| Apache Maven | Latest | Dependency management and build automation |
| IntelliJ IDEA / Eclipse | Latest | Integrated Development Environment |
| SQLite JDBC Driver | 3.42.0.0 | Database connectivity for SQLite |
| JFreeChart | 1.5.4 | Chart/graph generation library for summary reports |
| iText PDF Library | 7.1.3 | PDF generation for transaction reports |
| Swing (Java Standard) | Built-in | GUI framework for desktop application |

**Key Libraries:**
- **SQLite JDBC**: Provides database connectivity. SQLite was chosen for its lightweight, file-based nature - perfect for a desktop application that doesn't require a separate database server.
- **JFreeChart**: Enables creation of bar charts and other visualizations for the summary frame, showing spending patterns by category.
- **iText PDF**: Allows exporting transaction data to PDF format for archiving and reporting purposes.

All dependencies are managed through Maven's pom.xml file, making installation automatic when building the project.

2. **Project functions**  

The Expense Tracker application provides the following core functions:

| No | Function | Description |
| :---- | :---- | :---- |
| 1 | Add Transaction | Users can add income or expense transactions with details including amount, category, date/time, description, and currency (USD, EUR, VND). The system automatically converts all amounts to base currency (USD) for balance calculations. |
| 2 | View Transaction History | Displays all transactions in a scrollable list with cards showing transaction details. Users can see transactions sorted by date with income and expenses clearly differentiated. |
| 3 | Edit Transaction | Allows users to modify existing transactions. Users can change amount, category, description, date, and currency. Changes are immediately reflected in the balance calculation. |
| 4 | Delete Transaction | Removes transactions from the database. The balance is automatically recalculated after deletion. |
| 5 | View Current Balance | Shows the total balance calculated from all transactions (income minus expenses), converted to USD. Displayed prominently on the main screen. |
| 6 | Filter Transactions | Filter transactions by category, date range, or transaction type (income/expense). Used in transaction history and summary views. |
| 7 | Search Transactions | Search for transactions by description or category keywords. Provides quick access to specific transactions. |
| 8 | Sort Transactions | Sort transactions by date (ascending/descending) or by amount (ascending/descending) for better organization. |
| 9 | View Summary Report | Displays a bar chart showing spending/income categorized by category type. Users can filter by time period (Week/Month) and transaction type (Income/Expense). |
| 10 | Export to PDF | Generate PDF reports of transactions within a selected date range. PDF includes summary statistics (balance, total income, total expenses) and a detailed transaction table. |
| 11 | Currency Conversion | Automatically converts transactions in different currencies (EUR, VND) to USD for unified balance tracking. Exchange rates are configurable in TransactionController. |
| 12 | Categorization | Organize transactions into predefined categories:
- **Expense Categories**: Food and Drinks, Transport, Accommodation, Entertainment, Health and Beauty, Education, Gifts and Donations, Travel, Insurances, House and Garden, Technology, Other
- **Income Categories**: Salary, Investments, Gifts, Scholarships, Other |

3. **Database**  

The project uses SQLite, a lightweight, file-based relational database management system. SQLite stores all data in a single file (`data/expense.db`), making it ideal for desktop applications.

**Database Schema:**

**Table: transactions**

| Column Name | Data Type | Constraints | Description |
| :---- | :---- | :---- | :---- |
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | Unique identifier for each transaction, auto-generated |
| datetime | TEXT | NOT NULL | Transaction date and time in ISO format (e.g., "2025-11-27T10:00:00") |
| amount | REAL | NOT NULL | Transaction amount (positive for income, negative for expenses) |
| category | TEXT | NOT NULL | Transaction category (e.g., "Food and Drinks", "Salary") |
| description | TEXT | NULL | Optional description/note for the transaction |
| currency_code | VARCHAR(5) | NOT NULL DEFAULT 'USD' | Currency code (USD, EUR, VND) |

**Entity-Relationship Diagram (ERD):**

```
┌─────────────────────┐
│   transactions      │
├─────────────────────┤
│ PK id (INTEGER)     │
│    datetime (TEXT)  │
│    amount (REAL)    │
│    category (TEXT)  │
│    description      │
│    currency_code    │
└─────────────────────┘
```

The database has a single table since categories are stored as strings rather than foreign keys. This simplifies the schema while maintaining functionality.

**Database Initialization:**
```sql
CREATE TABLE IF NOT EXISTS transactions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    datetime TEXT NOT NULL,
    amount REAL NOT NULL,
    category TEXT NOT NULL,
    description TEXT,
    currency_code VARCHAR(5) NOT NULL DEFAULT 'USD'
);
```

**Data Storage:**
- All transaction data is persisted in `data/expense.db` file
- The database is automatically created on first run if it doesn't exist
- Data persists between application sessions
- No additional database server setup required

4. **GUI (User Interfaces)**  

The application consists of multiple graphical user interfaces:

| No | Interface Name | Purpose | Key Components |
| :---- | :---- | :---- | :---- |
| 1 | Main Frame | Main application window and navigation hub | Welcome label, current balance display, action buttons (Add Transaction, Browse History, View Report, Generate Report), money flow panel showing recent transactions |
| 2 | Add Transaction Frame | Form for adding new income/expense transactions | Transaction type selector (Income/Expense), category dropdown, amount input, date/time pickers, description text area, currency selector, Submit and Cancel buttons |
| 3 | Transaction History Frame | Displays all past transactions | Scrollable list of transaction cards, each showing transaction details (ID, date, amount, category, description), Edit and Delete buttons for each transaction |
| 4 | Summary Report Frame | Visual analytics and reporting | Period filter dropdown (Week/Month), transaction type filter (Income/Expense), bar chart showing amounts by category, Go Back button |
| 5 | Edit Transaction Dialog | Modal dialog for editing existing transactions | Pre-filled form fields matching Add Transaction Frame, Update and Cancel buttons |
| 6 | Export Date Range Dialog | Dialog for selecting date range for PDF export | Start date picker, end date picker, Export and Cancel buttons |

**Figure 1: Main Frame**

![Main Frame](main_frame.png)

The Main Frame serves as the application's home screen. When the application starts, users see:
- **Welcome Message**: "Welcome! What would you like to do today?"
- **Current Balance Display**: Shows the total balance (income minus expenses) in USD, prominently displayed
- **Action Buttons**:
  - **Add Transaction**: Opens the Add Transaction Frame. When clicked, it creates a new AddExpenseFrame instance and displays it.
  - **Browse History**: Opens the Transaction History Frame showing all past transactions
  - **View Report**: Opens the Summary Report Frame with charts
  - **Generate Report**: Opens the Export Date Range Dialog, then generates a PDF report
- **Money Flow Panel**: Shows recent transactions in a scrollable list below the balance

**Button Events:**
- All buttons use ActionListener interface. When clicked, `actionPerformed()` method in MainFrame is called with the button's action command.
- The method uses if-else statements to determine which frame to open based on the command string.

**Figure 2: Add Transaction Frame**

![Add Transaction Frame](add_transaction_frame.png)

The Add Transaction Frame allows users to input new transactions. Components include:
- **Transaction Type Panel**: Radio buttons to select "Expense" or "Income". Changing the selection updates the category dropdown to show appropriate categories.
- **Category Dropdown**: Shows expense categories (Food and Drinks, Transport, etc.) or income categories (Salary, Investments, etc.) based on transaction type.
- **Amount Field**: Text field with validation to accept only numeric input
- **Date Panel**: Dropdowns for selecting day, month, and year
- **Description Text Area**: Multi-line text area for transaction notes
- **Currency Selector**: Dropdown to choose USD, EUR, or VND
- **Submit Button**: Validates input, converts amount to base currency if needed, calls `controller.addTransaction()`, saves to database, and closes the frame
- **Cancel Button**: Closes the frame without saving

**Figure 3: Transaction History Frame**

![Transaction History Frame](transaction_history_frame.png)

Displays all transactions in chronological order. Features:
- **Transaction Cards**: Each transaction is displayed as a card showing:
  - Transaction ID
  - Date and time
  - Amount with currency
  - Category
  - Description
- **Edit Button** (on each card): Opens Edit Dialog pre-filled with transaction data. After update, calls `controller.updateTransaction()` and refreshes the display.
- **Delete Button** (on each card): Calls `controller.deleteTransaction()` with the transaction ID, removes from database, and refreshes the list.
- **Go Back Button**: Returns to Main Frame

**Figure 4: Summary Report Frame**

![Summary Report Frame](summary_frame.png)

Provides visual analytics of spending patterns. Includes:
- **Period Filter**: Dropdown to select "Week" or "Month" - filters transactions to the last week or month
- **Type Filter**: Dropdown to select "Expense" or "Income" - shows only that transaction type
- **Bar Chart**: JFreeChart bar graph showing amounts grouped by category. X-axis shows categories, Y-axis shows amounts (formatted to avoid scientific notation for large numbers).
- When filters change, the chart automatically updates using the FilterChangeListener interface pattern.
- **Go Back Button**: Returns to Main Frame

**Usage Examples:**
- To add a lunch expense: Click "Add Transaction" → Select "Expense" → Choose "Food and Drinks" → Enter amount → Select today's date → Add description "Lunch at restaurant" → Click Submit
- To view spending patterns: Click "View Report" → Select "Month" and "Expense" → See bar chart showing spending by category for the last month
- To export a report: Click "Generate Report" → Select date range → Choose save location → PDF file is generated with transaction summary and details 


9. **Conclusion**  
- Assess your project by discussing on its pros and the cons.  
- I want to hear what you really think about your project quality, and leave a score you think your project can gain along with the reason why you think so.  
- What more function you think you can improve and add to the project in the future.  
- …

**DUTY ROSTER**

| ID | Task  | In Charge | Start | End | State | Note |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| 1 | Design Backend (controller, model, database, mock CLI UI) | NguyenQuoc Khang | 14-Nov-25 | 21-Nov-25 | Done |  |
| 2 | Implement GUI, fix controller and model based on features  | Vuong Gia Kiet | 22-Nov-25 | 28-Nov-25 | Done |  |
| 3 | Report Section | Both | 29-Nov-25 | 30-Nov-25 | In progress |  |

**REFERENCE**

1. Tutorial Page, Oracle, 2024, https://...  
2. …  
     
     
   *\[Students, please put here whatever sources you referred or used in here\]*

APPENDIX A: CLASS DESCRIPTION

- Class 1 : Grade…. (Source: Src/Grade.java)  
- 

[image1]: <data:image/png;base64,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>

[image2]: <data:image/png;base64,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>