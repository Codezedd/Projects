import hashlib

#reading in our first three hex files for our dictionary attack attempt
hex_one = open("pw1.hex","r")
hex_two = open("pw2.hex","r")
hex_three = open("pw3.hex","r")
#ensuring we are dealing with hashes only, so we eliminate spaces from file
passwordone = hex_one.read().strip()
passwordtwo = hex_two.read().strip()
passwordthree = hex_three.read().strip()

hex_one.close()
hex_two.close()
hex_three.close()

#here we are opening the dictionary file: dic-0294.txt
file = open("dic-0294.txt","r")

dictionary = file.readlines()

file.close

#Trying various hashing alogrithms: sha1, sha256, md5
print ("testing for password one from hashes...")
for entry in dictionary:
    #reading in each line has these characters at end of entry, so here we take care of that
    entry = entry.strip('\n')
    
    hashtestone_sha1 = hashlib.sha1(entry.encode()).hexdigest()
    hashtestone_sha256 = hashlib.sha256(entry.encode()).hexdigest()
    hashtestone_md5 = hashlib.md5(entry.encode()).hexdigest()

    if hashtestone_sha1 == passwordone:
        print ("Password one is: ", end = "" )
        print (entry)

    elif hashtestone_sha256 == passwordone:
        print ("Password one is: ", end = "" )
        print (entry)    

    elif hashtestone_md5 == passwordone:
        print ("Password one is: ", end = "" )
        print (entry)


print ("testing for password two from hashes...")
for entry in dictionary:
    #reading in each line has these characters at end of entry, so here we take care of that
    entry = entry.strip('\n')
    
    hashtestone_sha1 = hashlib.sha1(entry.encode()).hexdigest()
    hashtestone_sha256 = hashlib.sha256(entry.encode()).hexdigest()
    hashtestone_md5 = hashlib.md5(entry.encode()).hexdigest()

    if hashtestone_sha1 == passwordtwo:
        print ("Password two is: ", end = "" )
        print (entry)

    elif hashtestone_sha256 == passwordtwo:
        print ("Password two is: ", end = "" )
        print (entry)    

    elif hashtestone_md5 == passwordtwo:
        print ("Password two is: ", end = "" )
        print (entry)   


print ("testing for password three from hashes...")
for entry in dictionary:
    #reading in each line has these characters at end of entry, so here we take care of that
    entry = entry.strip('\n')
    
    hashtestone_sha1 = hashlib.sha1(entry.encode()).hexdigest()
    hashtestone_sha256 = hashlib.sha256(entry.encode()).hexdigest()
    hashtestone_md5 = hashlib.md5(entry.encode()).hexdigest()

    if hashtestone_sha1 == passwordthree:
        print ("Password three is: ", end = "" )
        print (entry)

    elif hashtestone_sha256 == passwordthree:
        print ("Password three is: ", end = "" )
        print (entry)    

    elif hashtestone_md5 == passwordthree:
        print ("Password three is: ", end = "" )
        print (entry) 

####################
#Testing the salted hash passwords in this section
#reading in our hash hex files for our dictionary attack attempt
hash_one = open("spw1.hex","r")
hash_two = open("spw2.hex","r")
hash_three = open("spw3.hex","r")

#ensuring we are dealing with hashes only, so we eliminate spaces from file
password_hashone = hash_one.read().strip()
password_hashtwo = hash_two.read().strip()
password_hashthree = hash_three.read().strip()

hash_one.close()
hash_two.close()
hash_three.close()

#reading in our salt value
salt_file = open("salt.hex","r")
salt = salt_file.read()
salt_file.close()

#converting salt hex to bytes
salt_byte = bytes.fromhex(salt)

print("finding salted password one...")
#converting dictionary entry to byte
for entry in dictionary:

    entry = entry.strip('\n')

    byte_string = entry.encode()

#hashing our salt and entry together
    salted_entry = salt_byte + byte_string

    hashentry_sha1 = hashlib.sha1(salted_entry).hexdigest()
    hashentry_sha256 = hashlib.sha256(salted_entry).hexdigest()
    hashentry_md5 = hashlib.md5(salted_entry).hexdigest()

    if hashentry_sha1 == password_hashone:
        print ("Salted Password one is: ", end = "" )
        print (entry)

    if hashentry_sha256 == password_hashone:
        print ("Salted Password one is: ", end = "" )
        print (entry)
        
    if hashentry_md5 == password_hashone:
        print ("Salted Password one is: ", end = "" )
        print (entry)

print("finding salted password two...")
#converting dictionary entry to byte
for entry in dictionary:

    entry = entry.strip('\n')

    byte_string = entry.encode()

#hashing our salt and entry together
    salted_entry = salt_byte + byte_string

    hashentry_sha1 = hashlib.sha1(salted_entry).hexdigest()
    hashentry_sha256 = hashlib.sha256(salted_entry).hexdigest()
    hashentry_md5 = hashlib.md5(salted_entry).hexdigest()

    if hashentry_sha1 == password_hashtwo:
        print ("Salted Password two is: ", end = "" )
        print (entry)

    if hashentry_sha256 == password_hashtwo:
        print ("Salted Password two is: ", end = "" )
        print (entry)
        
    if hashentry_md5 == password_hashtwo:
        print ("Salted Password two is: ", end = "" )
        print (entry)

print("finding salted password three...")
#converting dictionary entry to byte
for entry in dictionary:

    entry = entry.strip('\n')

    byte_string = entry.encode()

#hashing our salt and entry together
    salted_entry = salt_byte + byte_string

    hashentry_sha1 = hashlib.sha1(salted_entry).hexdigest()
    hashentry_sha256 = hashlib.sha256(salted_entry).hexdigest()
    hashentry_md5 = hashlib.md5(salted_entry).hexdigest()

    if hashentry_sha1 == password_hashthree:
        print ("Salted Password three is: ", end = "" )
        print (entry)

    if hashentry_sha256 == password_hashthree:
        print ("Salted Password three is: ", end = "" )
        print (entry)
        
    if hashentry_md5 == password_hashthree:
        print ("Salted Password three is: ", end = "" )
        print (entry)

