
## Important Information Before Running the Program

Before running the program, please read the following:

Due to certain issues, some Lombok annotations may not have worked correctly in this project. Unfortunately, due to limited time, it was not possible to resolve these issues. As a result, setters and getters have been written explicitly instead of using Lombok annotations.

Thank you for your understanding and support!

# Important Information Before Running the Program

Before running the program, please read the following:

Due to certain issues, some Lombok annotations may not have worked correctly in this project. Unfortunately, due to limited time, it was not possible to resolve these issues. As a result, setters and getters have been written explicitly instead of using Lombok annotations.

If you have the time and opportunity, you can try to resolve the Lombok issue and replace the setters and getters with the appropriate Lombok annotations.

Thank you for your understanding and support!

# Instructions for Using Postman to Perform a Request

Follow these steps to make a request in Postman:

1. **Open Postman**  
   Launch the Postman application.

2. **Select the POST Method**  
   In the top menu, choose the `POST` method.

3. **Insert the URL**  
   Insert the following URL in the input field:  
   `http://localhost:8082/api/transfers`

4. **Go to the "Body" Tab**  
   Navigate to the `Body` tab.

5. **Select "form-data"**  
   Under the Body section, select the `form-data` option.

6. **Fill in the Parameters**  
   In the `Key` field, enter:
    - `playerId`
    - `teamId`  
      In the `Value` field, provide the following:
    - For `playerId`, provide the ID of the player you want to transfer to another team.
    - For `teamId`, provide the ID of the team you want to transfer the player to.

7. **Send the Request**  
   Click the `Send` button to execute the request.

The request will be sent to the server, and you will receive a response with the results of the operation.
