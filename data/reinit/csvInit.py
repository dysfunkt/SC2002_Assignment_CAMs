import csv

# Open the CSV file for reading
with open('input.csv', 'r') as input_file:
    # Create a CSV reader object
    reader = csv.reader(input_file)
    
    # Get the header row
    header = next(reader)
    
    # Add a new column header for UserID
    header.append('UserID')
    header.append('Password')
    
    # Create a list to hold the rows with the new column
    user = []

    
    # Loop through the remaining rows
    for row in reader:
        # Get the email address from the Email column
        email = row[header.index('Email')]
        
        # Parse the UserID from the email address
        user_id = email.split('@')[0]
        
        # Add the UserID to the row
        row.append(user_id)
        row.append("Password1")
        
        # Add the row to the list
        user.append(row)
    
    # Open a new CSV file for writing
    with open('output.csv', 'w', newline='') as output_file:
        # Create a CSV writer object
        writer = csv.writer(output_file)
        
        # Write the header row
        writer.writerow(header)
        
        # Write the rows with the new column
        writer.writerows(user)
