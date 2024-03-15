package com.fmt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * This class prints the data into xml and json files.
 */
public class Code {

	// main method to output the data's into respective .json files
	public static void main(String[] args) {
		HashMap<String, Person> personResult = DataConverter.dataPersonsParser();
		Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter("data/Persons.json")) // Output to Persons.json file
		{
			gson1.toJson(personResult, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		HashMap<String, Store> storeResult = DataConverter.dataStoresParser(personResult);
		Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter("data/Stores.json")) // Output to Stores.json file
		{
			gson2.toJson(storeResult, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		HashMap<String, Item> itemResult = DataConverter.dataItemsParser();
		Gson gson3 = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter("data/Items.json")) // Output to Items.json file
		{
			gson3.toJson(itemResult, writer);
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}