package frontend.app.summaryFrame.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CategoryUtil {
    private static final Set<String> EXPENSE_CATEGORIES = new HashSet<>(Arrays.asList(
            "Food and Drinks",
            "Transport",
            "Accommodation",
            "Entertainment",
            "Health and Beauty",
            "Education",
            "Gifts and Donations",
            "Travel",
            "Insurances",
            "House and Garden",
            "Technology",
            "Other"
    ));

    private static final Set<String> INCOME_CATEGORIES = new HashSet<>(Arrays.asList(
            "Salary",
            "Investments",
            "Gifts",
            "Scholarships",
            "Other"
    ));

    public static boolean isExpenseCategory(String category) {
        return EXPENSE_CATEGORIES.contains(category);
    }

    public static boolean isIncomeCategory(String category) {
        return INCOME_CATEGORIES.contains(category);
    }

    public static String[] getExpenseCategories() {
        return EXPENSE_CATEGORIES.toArray(new String[0]);
    }

    public static String[] getIncomeCategories() {
        return INCOME_CATEGORIES.toArray(new String[0]);
    }
}
