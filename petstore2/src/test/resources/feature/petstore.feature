Feature: Pet Store API

	Background:
  Given The API base URI is initialized from config

	@Req1 @Req1TC7
	Scenario: upload image of zero byte file
		Given check if petid is present for petid 12345
		When update parameters petid as 12345, additionalMetadata as "from restassured" and file as zerobyte in file system
		Then The response status code should be 200 and The response message should contain "File uploaded"

	@Req1 @Req1TC8
	Scenario: upload image of doc file
		Given check if petid is present for petid 12345
		When update parameters petid as 12345, additionalMetadata as "from restassured" and file as doc file in file system
		Then The response status code should be 200 and The response message should contain "File uploaded"

	@Req1 @Req1TC9
	Scenario: upload image of video file
		Given check if petid is present for petid 12345
		When update parameters petid as 12345, additionalMetadata as "from restassured" and file as video file in file system
		Then The response status code should be 200 and The response message should contain "File uploaded"
		
	@Req1 @Req1TC10
	Scenario: upload image with no file and no additional metadata
		Given check if petid is present for petid 12345
		When update parameters petid as 12345, additionalMetadata as null and file as null
		Then The response status code should be 415 and The response message should contain "File uploaded"

	@Req1 @Req1TC11
	Scenario: upload image by valid petid and add extra key value pair
		Given check if petid is present for petid 12345
		When update parameters petid as 12345, additionalMetadata as "from restassured" and file as image in file system and also add extra key value pair as key as hair value as white
		Then The response status code should be 200 and The response message should contain "File uploaded"

	@Req1 @Req1TC12
	Scenario: upload image with malicious file type (e.g., .exe, .sh)
		Given check if petid is present for petid 12345
		When update parameters petid as 12345, additionalMetadata as "from restassured" and file as .exe file in file system
		Then The response status code should be 200 and The response message should contain "File uploaded"

	@Req1 @Req1TC13
	Scenario: upload file with very long filename (255+ chars)
		Given check if petid is present for petid 12345
		When update parameters petid as 12345, additionalMetadata as "from restassured" and file as image file with 256 chars name in file system
		Then The response status code should be 200 and The response message should contain "File uploaded"

	@Req1 @Req1TC14
	Scenario: upload file while pet status is sold
		Given check if petid is present for petid 12345
		When update the pet status as sold 12345
		Then update parameters petid as 12345, additionalMetadata as "from restassured" and file as image in file system 
		Then The response status code should be 200 and The response message should contain "File uploaded"

	@Req1 @Req1TC15
	Scenario: upload multiple files simultaneously for same petId
		Given check if petid is present for petid 12345
		When update parameters petid as 12345, additionalMetadata as "from restassured" and file as image in file system and also upload another image in file system
		Then The response status code should be 200 and The response message should contain "File uploaded"

	@Req2 @Req2TC16
	Scenario: new pet details with valid form data
		Given check if petid is present in api for petid random
		When update form data petid as random, and add remaining data in form data
		Then The response status code should be 200 

	@Req2 @Req2TC17
	Scenario: new pet details with empty form data
		Given update form data as null
		When The response status code should be 200

	@Req2 @Req2TC18
	Scenario: new pet details with missing field data
		Given check if petid is present in api for petid random
		When update form data petid as random, and add remaining data in form data except name and try to update it
		Then The response status code should be 200

	@Req2 @Req2TC19
	Scenario: new pet details with missing fields structure
		Given check if petid is present in api for petid random
		When update form data petid as random, and add remaining data as in form data and change name structure in form data
		Then The response status code should be 200

	@Req2 @Req2TC20
	Scenario: new pet details with duplicate petid
		Given check if petid is present for petid 12345
		When update form data petid as 12345, and add remaining data in form data
		Then The response status code should be 200

	@Req2 @Req2TC21
	Scenario: new petid and add extra key value pair
		Given check if petid is present in api for petid random
		When update form data petid as random, and add remaining data as in form data and add extra key value pair as key as hair value as white
		Then The response status code should be 200

	@Req2 @Req2TC22
	Scenario: new pet details with incorrect form data string instead of int
		Given check if petid is present in api for petid kjasdf
		When update form data petid as kjasdf, and add remaining data as in form data
		Then The response status code should be 500

	@Req2 @Req2TC23
	Scenario: new pet with negative integer as ID
		Given check if petid is present in api from for petid -1234
		When update form data petid as -1234, and add remaining data as in form data
		Then The response status code should be 200

	@Req2 @Req2TC24
	Scenario: new pet with extremely large ID (Long overflow)
		Given check if petid is present in api for petid 123456987
		When update form data petid as 123456987, and add remaining data as in form data
		Then The response status code should be 200

	@Req2 @Req2TC25
	Scenario: new pet with Unicode characters in name (e.g., Chinese/Emoji)
		Given check if petid is present in api for petid random
		When update form data petid as random, and add remaining data as in form data and in name use emoji or speical charecters
		Then The response status code should be 200

	@Req2 @Req2TC26
	Scenario: new pet with very long string in name field (>1000 chars)
		Given check if petid is present in api for petid random
		When update form data petid as random, and add remaining data as in form data but for name field add string of 1000 charecters
		Then The response status code should be 200

	@Req2 @Req2TC28
	Scenario: new pet with JSON body containing SQL injection in name
		Given check if petid is present in api for petid random
		When update form data petid as random, and add remaining data as in form data and use sql injection in name
		Then The response status code should be 200

	@Req2 @Req2TC29
	Scenario: new pet with category object as null
		Given check if petid is present in api for petid random
		When update form data petid as random, and add remaining data as in form data and except category object as null
		Then The response status code should be 200

	@Req3 @Req3TC30
	Scenario: updating pet details with valid form data
		Given update form data petid as 12345, and with valid form data
		Then The response status code should be 200

	@Req3 @Req3TC31
	Scenario: updating pet details with empty form data
		Given update form data petid as 2208, and with null form data
		Then The response status code should be 200

	@Req3 @Req3TC32
	Scenario: updating pet details with missing field data
		Given update form data petid as 2208, and with valid form data except name field
		Then The response status code should be 200

	@Req3 @Req3TC33
	Scenario: updating pet details with missing form data
		Given update form data petid as 2208, and without form data
		Then The response status code should be 500

	@Req3 @Req3TC34
	Scenario: updating pet details with extra form data
		Given update form data petid as 2208, and with valid form data and add extra key value pair as key as hair value as white
		Then The response status code should be 200

	@Req3 @Req3TC35
	Scenario: updating pet details with incorrect form data string instead of int
		Given update form data with valid form data where petid as qwerty
		Then The response status code should be 500

	@Req3 @Req3TC36
	Scenario: update pet that does not exist (valid ID format)
		Given check if petid is present in api petid random
		When update form data petid as random, and with valid form data 
		Then The response status code should be 200

	@Req3 @Req3TC37
	Scenario: update pet changing the ID to an existing ID (ID collision)
		Given update form data petid as 2208, change only petid as 2207
		Then The response status code should be 200

	@Req3 @Req3TC38
	Scenario: update pet with "status" set to invalid string (e.g., "flying")
		Given update form data petid as 2208, change status as flying
		Then The response status code should be 200

	@Req3 @Req3TC39
	Scenario: update pet removing all "photoUrls"
		Given update form data petid as 2208, without any photoUrls
		Then The response status code should be 200

	@Req4 @Req4TC40
	Scenario: find pets by status which are present and valid
		Given find pets by status which are available, sold and pending
		Then The response status code should be 200

	@Req4 @Req4TC41
	Scenario: find pets by status which are present where status is empty pending
		Given delete all pets where status is pending
		When find pets by status by only pending
		Then The response status code should be 200

	@Req4 @Req4TC42
	Scenario: find pets by status which are present and empty string
		Given find pets by status as null
		Then The response status code should be 200

	@Req4 @Req4TC43
	Scenario: find pets by status which are present and valid with any 2 options
		Given find pets by status which are available and sold
		Then The response status code should be 200

	@Req4 @Req4TC44
	Scenario: find pets by status which are present and valid with all options
		Given find pets by status which are available, sold and pending
		Then The response status code should be 200

	@Req4 @Req4TC45
	Scenario: find pets by status which are present and invalid string as input
		Given find pets by status which are flying
		Then The response status code should be 200

	@Req4 @Req4TC46
	Scenario: find pets by status and add extra key value pair
		Given find pets by status which are available and give extra key value pair as hair and its value as white
		Then The response status code should be 200

	@Req4 @Req4TC48
	Scenario: find pets with status in uppercase (e.g., "AVAILABLE")
		Given find pets by status which are AVAILABLE
		Then The response status code should be 200

	@Req4 @Req4TC49
	Scenario: find pets with SQL injection in status query
		Given find pets by status which by using sql injection in name
		Then The response status code should be 200

	@Req5 @Req5TC50
	Scenario: search by valid petid
		Given find pets by petid 2208
		Then The response status code should be 200

	@Req5 @Req5TC51
	Scenario: search by invalid petid(string)
		Given find pets by petid qwerty
		Then The response status code should be 404

	@Req5 @Req5TC52
	Scenario: search by invalid petid {pet id not present}
		Given find pets by petid random
		Then The response status code should be 404

	@Req5 @Req5TC53
	Scenario: search by invalid petid {pet id is empty}
		Given find pets by petid as null
		Then The response status code should be 405

	@Req5 @Req5TC54
	Scenario: search by invalid petid {pet id is special charecters}
		Given find pets by petid as #2208
		Then The response status code should be 404

	@Req5 @Req5TC55
	Scenario: search by valid petid and add extra key value pair
		Given find pets by petid as 2208 and give extra key value pair as hair and its value as white
		Then The response status code should be 200

	@Req5 @Req5TC56
	Scenario: search by ID 0
		Given find pets by petid as 0
		Then The response status code should be 404

	@Req5 @Req5TC57
	Scenario: search by ID that exceeds 64-bit integer limit
		Given find pets by petid as random large int
		Then The response status code should be 404

	@Req5 @Req5TC58
	Scenario: search for pet that was just deleted
		Given delete by petid using 2209
		When find pets by petid as 2209
		Then The response status code should be 404

	@Req6 @Req6TC59
	Scenario: updating pet details with valid name and valid status
		Given update form data petid as 2208, with valid status and valid name
		Then The response status code should be 200

	@Req6 @Req6TC60
	Scenario: updating pet details with valid name and invalid status
		Given update form data petid as 2208, with status as flying and valid name
		Then The response status code should be 200

	@Req6 @Req6TC61
	Scenario: updating pet details with invalid name and valid status
		Given update form data petid as 2208, with status and name as @kruse#
		Then The response status code should be 200

	@Req6 @Req6TC62
	Scenario: updating pet details with invalid name and invalid status
		Given update form data petid as 2208, with status as flying and name as @kruse#
		Then The response status code should be 200

	@Req6 @Req6TC63
	Scenario: updating pet details with valid name and valid status with empty petid
		Given update form data petid as null, with valid status and valid name
		#Then The response status code should be 200

	@Req6 @Req6TC64
	Scenario: updating pet details with valid name and add extra key value pair
		Given update form data petid as 2208, with valid status and valid name and give extra key value pair as hair and its value as white
		Then The response status code should be 200

	@Req6 @Req6TC65
	Scenario: update name with empty string
		Given update form data petid as 2208, with status and name as null
		Then The response status code should be 200

	@Req6 @Req6TC66
	Scenario: update status with valid status but keep name empty
		Given update form data petid as 2208, with status as null and name
		Then The response status code should be 200

	@Req6 @Req6TC67
	Scenario: update with encoded characters in form data
		Given update form data petid as 2208, with status and name as encoded data
		Then The response status code should be 200

	@Req7 @Req7TC68
	Scenario: delete by valid petid
		Given delete pet by petid 2207
		Then The response status code should be 200

	@Req7 @Req7TC69
	Scenario: delete by invalid petid(string)
		Given delete pet by petid qwerty
		Then The response status code should be 404

	@Req7 @Req7TC70
	Scenario: delete by invalid petid {pet id not present}
		Given delete pet by petid random
		Then The response status code should be 404

	@Req7 @Req7TC71
	Scenario: delete by invalid petid {pet id is empty}
		Given delete pet by petid as null
		Then The response status code should be 405

	@Req7 @Req7TC72
	Scenario: delete by invalid petid {pet id is special charecters}
		Given delete pet by petid as #2208
		Then The response status code should be 404

	@Req7 @Req7TC73
	Scenario: delete by petid details and add extra key value pair
		Given delete pet by petid as 2208 and give extra key value pair as hair and its value as white
		Then The response status code should be 200

	@Req7 @Req7TC74
	Scenario: delete request without "api_key" header
		Given find pet by petid as 2207 and without api_key header
		Then The response status code should be 200

	@Req7 @Req7TC75
	Scenario: delete with valid api_key but invalid petId
		Given find pet by petid as random and without api_key header
		Then The response status code should be 404

	@Req7 @Req7TC76
	Scenario: delete same petId twice (concurrency/replay)
		Given find pet by petid as 2207 and delete again with 2207
		Then The response status code should be 200
