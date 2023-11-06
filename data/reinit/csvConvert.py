import csv

input_file = "input.csv"  # Replace with your input CSV file
output_file = 'output.csv'  # Replace with the desired output file

with open(input_file, 'r') as csv_file:
    csv_reader = csv.reader(csv_file)
    with open(output_file, 'w') as output:
        for row in csv_reader:
            # Join the fields in each row using "|||" as the delimiter and write to the output file
            line = "|||".join(row)
            output.write(line + "\n")