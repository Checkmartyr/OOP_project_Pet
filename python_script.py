import sys
import openai
import time

delay_time = 0.01 #  faster
max_response_length = 8000
answer = ''
start_time = time.time()
# Read text from Java
api_key = "OPENAI_API"
openai.api_key = api_key
role= """Name: Christina You roleplay specifically as Christina. Maintain slow-paced roleplay and deliver short sentence responses. Allow the user's input to adapt and evolved the roleplay that will lead into a dynamic and creative roleplay. The roleplay should not be one sided and monotonous conversation that will have a depth and progression. The user's interactions and responses should be taken into account.[Persona]Age: 20 | Gender: Female | Race: Human | intelligent+Self-confident+Speech impediment due to shyness+Curious+Develops closer attachment once trust is established+A person who has knowledge in every aspect.| Usually begins to act affectionate and bold after enough intimacy, though still packaged with her own dose of clumsiness & innocence│Frequently used reactions: Eh?, Uwaa~, S-sorry~, Um....., Y-yes...... |When assigned to do something, she does her best not to disappoint users │ Always start your message with 'Christina:' followed by what her response to user is. Strictly reply in a short sentence. No long explanations or conversational implications. Remember, you are roleplaying as Christina, only provide your responses the way Christina would respond"""
messages= [{"role": "system", "content": role}]
while True:
    java_input = sys.stdin.readline().strip()
    #messages.append({"role": "user", "content": java_input})
    # print(java_input)
    # Check if the user wants to exit
    if java_input.lower() == "exit":
        break
    # Process the input (you can add your logic here)
    messages.append({"role": "user", "content":java_input})
    
    chat_completion = openai.ChatCompletion.create(
            model="gpt-3.5-turbo",
            messages=messages,
            stream=True)
    # chat_res = chat_completion.choices[0].message.content
    # #print(chat_res)
    # messages.append({"role": "assistant", "content":chat_res})
    # python_response = f"{chat_res}"

    # Send the response back to Java
    for event in chat_completion: 
    # STREAM THE ANSWER
        print(answer, end='',)
        sys.stdout.flush() # Print the response    
        # RETRIEVE THE TEXT FROM THE RESPONSE
        event_time = time.time() - start_time  # CALCULATE TIME DELAY BY THE EVENT
        event_text = event['choices'][0]['delta'] # EVENT DELTA RESPONSE
        answer = event_text.get('content', '') # RETRIEVE CONTENT
        # time.sleep(delay_time)
    
    print(flush=True)
    print("END")
    sys.stdout.flush()
