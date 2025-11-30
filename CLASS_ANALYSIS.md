# II. CLASS ANALYSIS

## 1. Objects

Analyse and list objects (name, behavior, state)
Group objects having common states and behaviors.

### Table 1. List of Objects (Transactions, Frames, Panels, Controllers)

| No | Object Name | State | Behaviours |
|----|-------------|-------|------------|
| 1 | Transaction 1 | id: 1, amount: 100.0, category: "Food", description: "Lunch", dateTime: 2025-01-15T12:00, currencyCode: "USD" | getId, getAmount, getCategory, getDescription, getDateTime, getDate, getCurrencyCode, setAmount, setCategory, setDescription, setDateTime, setCurrencyCode, getFormattedDateTime, toString |
| 2 | TransactionController | EXCHANGE_RATES: Map<String, Double>, controller instance | addTransaction, deleteTransaction, updateTransaction, getAllTransactions, findById, getCurrentBalance, filterByCategory, filterByDateRange, filterByType, sortByDate, sortByAmount, search, getUniqueCategories, convertToBaseCurrency, getBaseCurrencyAmount |
| 3 | Database | URL: "jdbc:sqlite:data/expense.db" | getConnection, init, insertSampleData |
| 4 | MainFrame | title: "Expense Tracker", width: 600, height: 800, controller: TransactionController | addGuiComponents, addWelcomeLabel, addBalanceLabel, addBalanceTextField, addAddTransactionButton, addBrowseHistoryButton, addViewReportButton, addGenerateReportButton, addMoneyFlowPanel, calculateBalance, actionPerformed |
| 5 | AddExpenseFrame | title: "Add Transaction", width: 600, height: 800, controller: TransactionController, amountPanel, categoryPanel, datePanel, descriptionPanel, transactionTypePanel, buttonPanel | addGuiComponents, addPanels, initializePanels, arrangePanels, addWelcomingComponents, getAmountPanel, getCategoryPanel, getDatePanel, getDescriptionPanel, getTransactionTypePanel |
| 6 | AmountPanel | width: int, amountEnteringTextField: JTextField, currencyComboBox: JComboBox | addAmountEnteringComponents, createAmountLabel, createAmountTextField, createCurrencyComboBox, getAmountEnteringTextField, getCurrencyComboBox, getAmountText, setAmountText, clear |
| 7 | CategoryPanel | width: int, isExpense: boolean, categoryComboBox: JComboBox | addCategoryComponents, createCategoryLabel, createCategoryComboBox, createExpenseCategoriesArray, createIncomeCategoriesArray, updateCategories, getCategoryComboBox |
| 8 | HistoryCard | id: int, amount: double, dateTime: LocalDateTime, category: String, description: String, currencyCode: String, source: HistoryCardsPanel | initializeCardLook, addComponents, createCategoryLabel, createAmountLabel, createDateLabel, createDescriptionTextArea, createButtonPanel, createDeleteButton, createEditButton, getId, getAmount, getDateTime, getCategory, getDescription, getCurrencyCode |
| 9 | ChartPanel | controller: TransactionController, chartPanelComponent: ChartPanel, dataset: DefaultCategoryDataset | updateChart, getFilteredTransactions, groupByCategory, setBounds |
| 10 | PDFExporter | filePath: String, transactionList: List<Transaction>, startDate: LocalDate, endDate: LocalDate, controller: TransactionController, headers: String[] | exportFile, createSummaryTable, createTransactionTable, populateTransactionTable, populateSummaryTable, addSummaryCell, addTransactionCell, addTransactionHeaders, createReportDateBoundsInfo, createReportGeneratedDate, createBalanceInfo, createTotalExpensesInfo, createTotalIncomeInfo |

## 2. Classes

Create classes (name, attributes, functions) for each group. List the classes accordingly.
You can use the table containing objects and their class.
Analyse the inheritance among classes, Abstract classes
Draw a diagram to show the inheritance (Note: at the analysis stage, please do not provide the details of the classes such as data types, variable names, return datatype of methods and the param lists of the methods).

### Table 2. Class Analysis

#### 2.1 Model Classes

| Class Name | Attributes | Functions |
|------------|------------|-----------|
| Transaction | id, amount, category, description, dateTime, date, currencyCode | getId, getAmount, getCategory, getDescription, getDateTime, getDate, getCurrencyCode, setAmount, setCategory, setDescription, setDateTime, setCurrencyCode, getFormattedDateTime, toString |
| Category | name | (constructor) |

#### 2.2 Controller Classes

| Class Name | Attributes | Functions |
|------------|------------|-----------|
| TransactionController | EXCHANGE_RATES (static) | addTransaction, deleteTransaction, updateTransaction, getAllTransactions, findById, getCurrentBalance, filterByCategory, filterByDateRange, filterByType, sortByDate, sortByAmount, search, getUniqueCategories, convertToBaseCurrency, getBaseCurrencyAmount |

#### 2.3 Database Classes

| Class Name | Attributes | Functions |
|------------|------------|-----------|
| Database | URL (static) | getConnection, init, insertSampleData |

#### 2.4 View/UI Classes

| Class Name | Attributes | Functions |
|------------|------------|-----------|
| ConsoleView | controller, sc (Scanner) | start, addTransaction, showTransactions, deleteTransaction |
| TransactionUI | (utility methods) | addTransactionUI, viewAllUI, updateTransactionUI, deleteTransactionUI, filterSortSearchUI |
| PDFExporter | filePath, transactionList, startDate, endDate, controller, headers | exportFile, createSummaryTable, createTransactionTable, populateTransactionTable, populateSummaryTable, addSummaryCell, addTransactionCell, addTransactionHeaders, createReportDateBoundsInfo, createReportGeneratedDate, createBalanceInfo, createTotalExpensesInfo, createTotalIncomeInfo |

#### 2.5 Frame Classes

| Class Name | Attributes | Functions |
|------------|------------|-----------|
| BaseFrame (Abstract) | controller | initializeComponents, getController, createSeparator, addGuiComponents (abstract) |
| MainFrame | (inherits from BaseFrame) | addGuiComponents, addWelcomeLabel, addBalanceLabel, addBalanceTextField, addAddTransactionButton, addBrowseHistoryButton, addViewReportButton, addGenerateReportButton, addMoneyFlowPanel, calculateBalance, actionPerformed |
| AddExpenseFrame | amountPanel, transactionTypePanel, categoryPanel, datePanel, descriptionPanel, buttonPanel | addGuiComponents, addPanels, initializePanels, arrangePanels, addWelcomingComponents, getAmountPanel, getCategoryPanel, getDatePanel, getDescriptionPanel, getTransactionTypePanel |
| TransactionHistoryFrame | historyCardsScrollPane, historyCardsPanel, transactionHistoryButtonPanel | addGuiComponents, addPanels, initializePanels, arrangePanels, addWelcomingComponents, createTransactionHistoryLabel, getController, refreshTransactionFrame |
| SummaryFrame | filterPanel, chartPanel, buttonPanel | addGuiComponents, addPanels, initializePanels, arrangePanels, addWelcomingComponents, createSummaryLabel |

#### 2.6 Panel Classes

| Class Name | Attributes | Functions |
|------------|------------|-----------|
| AmountPanel | amountEnteringTextField, currencyComboBox | addAmountEnteringComponents, createAmountLabel, createAmountTextField, createCurrencyComboBox, getAmountEnteringTextField, getCurrencyComboBox, getAmountText, setAmountText, clear |
| CategoryPanel | categoryComboBox | addCategoryComponents, createCategoryLabel, createCategoryComboBox, createExpenseCategoriesArray, createIncomeCategoriesArray, updateCategories, getCategoryComboBox |
| DatePanel | (various date components) | (date selection methods) |
| DescriptionPanel | descriptionTextArea | (description input methods) |
| TransactionTypePanel | expenseCheckBox, incomeCheckBox, categoryPanel, isExpense | addCheckBoxes, createTypeCheckingCheckBoxes, getExpenseCheckBox, getIncomeCheckBox, getIsExpense, setExpense |
| AddExpenseFrameButtonPanel | sourceFrame, controller, amountEnteringTextField, descriptionTextArea, categoryComboBox, currencyComboBox, transactionTypePanel, datePanel | addButtons, createGoBackButton, createAddButton, createGoBackButtonActionListener, createAddTransactionActionListener, clearAllTheFieldsUponAdding |
| HistoryCard | id, amount, dateTime, category, description, currencyCode, source | initializeCardLook, addComponents, createCategoryLabel, createAmountLabel, createDateLabel, createDescriptionTextArea, createButtonPanel, createDeleteButton, createEditButton, getId, getAmount, getDateTime, getCategory, getDescription, getCurrencyCode |
| HistoryCardsPanel | source, controller | (card management methods) |
| EditDialog | (edit form components) | (edit dialog methods) |
| EditButtonPanel | source | addButtons, createCancelButton, createSaveButton, createSaveButtonActionListener |
| TransactionHistoryButtonPanel | source, controller | addButtons, createGoBackButton, createGoBackButtonActionListener |
| FilterPanel | periodComboBox, typeComboBox, listener | addFilterComponents, createPeriodLabel, createPeriodComboBox, createTypeLabel, createTypeComboBox, getSelectedPeriod, getSelectedType |
| ChartPanel | controller, chartPanelComponent, dataset | updateChart, getFilteredTransactions, groupByCategory, setBounds |
| SummaryFrameButtonPanel | source, controller | addButtons, createGoBackButton, createGoBackButtonActionListener |
| MoneyFlowPanel | controller | (money flow display methods) |

#### 2.7 Component/Utility Classes

| Class Name | Attributes | Functions |
|------------|------------|-----------|
| UIComponentFactory | (static methods only) | createLabel, createImageLabel, createTextField, createButton, createStringComboBox, createTextArea, createCheckBox, createSeparator |
| IconLoader | (static methods) | loadIcon |
| TransactionFlowFilter | (static methods) | validateAmountEntered, filterAmountEntered |
| ExpenseWrapper | (static) | setIsExpense, isExpense |
| AmountDocumentFilter | (document filter) | insertString, replace |
| DateDocumentFilter | (document filter) | insertString, replace |
| LimitedDocument | (document) | (character limit methods) |
| PlaceholderTextField | placeholder | (placeholder text methods) |
| CategoryUtil | (static methods) | isExpenseCategory, isIncomeCategory, getExpenseCategories, getIncomeCategories |

### 2.8 Inheritance Analysis

#### Inheritance Diagram

```
JFrame (Java Swing)
    └── BaseFrame (Abstract)
            ├── MainFrame
            ├── AddExpenseFrame
            ├── TransactionHistoryFrame
            └── SummaryFrame

JPanel (Java Swing)
    ├── AmountPanel
    ├── CategoryPanel
    ├── DatePanel
    ├── DescriptionPanel
    ├── TransactionTypePanel
    ├── AddExpenseFrameButtonPanel
    ├── HistoryCard
    ├── HistoryCardsPanel
    ├── EditButtonPanel
    ├── TransactionHistoryButtonPanel
    ├── FilterPanel
    ├── ChartPanel
    ├── SummaryFrameButtonPanel
    └── MoneyFlowPanel

DocumentFilter (Java Swing)
    ├── AmountDocumentFilter
    ├── DateDocumentFilter
    └── LimitedDocument

ActionListener (Java Interface)
    └── MainFrame (implements)
```

#### Inheritance Relationships:

1. **Frame Inheritance:**
   - `BaseFrame` (abstract) extends `JFrame`
   - All application frames (`MainFrame`, `AddExpenseFrame`, `TransactionHistoryFrame`, `SummaryFrame`) extend `BaseFrame`
   - `BaseFrame` provides common frame initialization and layout functionality

2. **Panel Inheritance:**
   - All panel classes extend `JPanel`
   - Each panel serves a specific UI component purpose (input, display, filtering, etc.)

3. **Document Filter Inheritance:**
   - Filter classes extend `DocumentFilter` to provide input validation and formatting

4. **Interface Implementation:**
   - `MainFrame` implements `ActionListener` to handle button click events

### 2.9 Class Grouping by Responsibility

**Model Layer (Data Representation):**
- Transaction
- Category

**Controller Layer (Business Logic):**
- TransactionController

**Database Layer (Data Persistence):**
- Database

**View Layer (Presentation):**
- ConsoleView
- TransactionUI
- PDFExporter

**Frame Layer (Window Management):**
- BaseFrame (Abstract)
- MainFrame
- AddExpenseFrame
- TransactionHistoryFrame
- SummaryFrame

**Panel Layer (UI Components):**
- Input Panels: AmountPanel, CategoryPanel, DatePanel, DescriptionPanel, TransactionTypePanel
- Action Panels: AddExpenseFrameButtonPanel, EditButtonPanel, TransactionHistoryButtonPanel, SummaryFrameButtonPanel
- Display Panels: HistoryCard, HistoryCardsPanel, ChartPanel, MoneyFlowPanel, FilterPanel
- Dialog: EditDialog

**Utility/Helper Layer:**
- UIComponentFactory
- IconLoader
- TransactionFlowFilter
- ExpenseWrapper
- CategoryUtil
- Input Filters: AmountDocumentFilter, DateDocumentFilter, LimitedDocument
- PlaceholderTextField

### 2.10 Key Design Patterns

1. **Template Method Pattern:** `BaseFrame` defines the template for frame initialization, with subclasses implementing specific GUI components

2. **Factory Pattern:** `UIComponentFactory` creates standardized UI components

3. **MVC Pattern:** 
   - Model: Transaction, Category
   - View: Frames, Panels
   - Controller: TransactionController

4. **Strategy Pattern:** Different document filters (AmountDocumentFilter, DateDocumentFilter) implement validation strategies

