// Ash Apsangi 10/23/2023
// This program removes multiple consecutive spaces from the file while preserving the formatting of the file.

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class fileFormatter {
	public static void main(String[] args) throws IOException {
		Scanner s = new Scanner(System.in);

		// Ask the user to input the file path
		System.out.print("Enter the file path to edit: ");
		String inputFilePath = s.nextLine();

		// Generate a unique temporary file name
		String tempFileName = createUniqueName("C:\\CS280\\TempX.txt");
		File tempFile = new File(tempFileName);
		;

		try {

			FileInputStream readData = new FileInputStream(inputFilePath);
			FileOutputStream writeData = new FileOutputStream(tempFile);

			int previousChar = -1; // To keep track of the previous character read
			boolean isTitle = true;

			int currentChar;

			while ((currentChar = readData.read()) != -1) {

				// Identify the title section and remove consecutive spaces
				if (isTitle && (currentChar == '\n' || currentChar == '\r')) {
					isTitle = false;
				}

				if (!isTitle) {
					if (currentChar == ' ' && previousChar == ' ') {
						continue;
					}
				}
				writeData.write(currentChar);
				previousChar = currentChar;
			}

			readData.close();
			writeData.close();

		} catch (

		IOException e) {
			System.err.println("An error occurred while processing the file: " + e.getMessage());
		}

		// Copy the contents of the temporary file back into the original file
		copyFile(tempFile, inputFilePath);

		// Remove the temporary file
		if (tempFile.exists()) {
			if (tempFile.delete()) {
				System.out.println("Temporary file deleted.");
			} else {
				System.err.println("Failed to delete the temporary file.");
			}
		}
		s.close();
	}

	private static void copyFile(File sourceFile, String inputFilePath) {
		// Uses Scanner and PrintWriter to copy contents from temp file to input file.

		try {
			Scanner i = new Scanner(sourceFile);
			PrintWriter outputFileWriter = new PrintWriter(inputFilePath);

			while (i.hasNextLine()) {
				String s = i.nextLine();
				outputFileWriter.println(s);
			}
			System.out.println("File edited successfully.");
			i.close();
			outputFileWriter.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found and error copying files: " + e.getMessage());
		}
	}

	private static String createUniqueName(String uniqueFileName) {
		while (new File(uniqueFileName).exists()) {
			// Removes .txt from uniqueFileName and concatenates "X.txt"
			uniqueFileName = uniqueFileName.substring(0, uniqueFileName.length() - 4) + "X.txt";
		}

		return uniqueFileName;
	}
}
