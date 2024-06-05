package RatingApp.com.entities;

import java.util.Arrays;
import java.util.List;

public class ChartData {
    private int[] earnings;
    private int[] expenses;
    private int[] breakUp;
    private String[] department;
    

    // Getters and setters
    public int[] getEarnings() {
        return earnings;
    }

    public void setEarnings(int[] earnings) {
        this.earnings = earnings;
    }

    public int[] getExpenses() {
        return expenses;
    }

    public void setExpenses(int[] expenses) {
        this.expenses = expenses;
    }

	public void setDateRates(List<String> dateRates) {
		// TODO Auto-generated method stub
	}

	@Override
	public String toString() {
		return "ChartData [earnings=" + Arrays.toString(earnings) + ", expenses=" + Arrays.toString(expenses) + "]";
	}

	public int[] getBreakUp() {
		return breakUp;
	}

	public void setBreakUp(int[] breakUp) {
		this.breakUp = breakUp;
	}

	public String[] getDepartment() {
		return department;
	}

	public void setDepartment(String[] department) {
		this.department = department;
	}

	
	
	
}

