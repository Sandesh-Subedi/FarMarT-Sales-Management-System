package com.fmt;

import java.util.ArrayList;
import java.util.Comparator;

@SuppressWarnings("serial")
public class SortedList<E> extends ArrayList<E> {
	public static Comparator<Invoice> cmpName = new Comparator<Invoice>() {
		public int compare(Invoice i1, Invoice i2) {
			// compare i1's customers last name to i2's customers last name
			int result = i1.getCustomer().getLastName().compareTo(i2.getCustomer().getLastName());
			// if the last names are the same, compare the first names
			if (result == 0) {
				result = i1.getCustomer().getFirstName().compareTo(i2.getCustomer().getFirstName());
			}
			return result;
		}
	};

	public static Comparator<Invoice> cmpVal = new Comparator<Invoice>() {
		public int compare(Invoice i1, Invoice i2) {
			// compare i1's grand total to i2's
			if (i1.totalGrandTotal() == i2.totalGrandTotal()) {
				return 0;
			} else if (i1.totalGrandTotal() > i2.totalGrandTotal()) {
				return -1; // i1 comes before i2
			} else {
				return 1; // i2 comes before i1
			}
		}
	};

	public static Comparator<Invoice> cmpStoreSales = new Comparator<Invoice>() {
		public int compare(Invoice i1, Invoice i2) {
			// compare i1's store code to i2's store code
			int cmp = i1.getStore().getStoreCode().compareTo(i2.getStore().getStoreCode());
	        // if store codes are equal, compare sales person last name and first name
			if (cmp == 0) {
				cmp = i1.getSalesPerson().getLastName().compareTo(i2.getSalesPerson().getLastName());
				if (cmp == 0) {
					cmp = i1.getSalesPerson().getFirstName().compareTo(i2.getSalesPerson().getFirstName());
				}
			}
			return cmp;
		}
	};

	// determine the index when the specified element should be inserted into the list
	private int insertIndex(E e, Comparator<E> comparator) {
		int low = 0;
		int high = size() - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			int cmp = comparator.compare(e, get(mid));
			if (cmp < 0) {
				high = mid - 1;
			} else if (cmp > 0) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return low;
	}

	// adds the specified element to the end of the list
	public boolean add(E e, Comparator<E> comparator) {
		int index = insertIndex(e, comparator);
		super.add(index, e);
		return true;
	}

}