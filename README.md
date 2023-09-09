# JavaWOL
This is a JavaFX application to wake machines through Wake On Lan interface

## USAGE

### CSV file format

**key terms**
- Mac address - a unique identifier passed to each network interface device i.e. FF:FF:FF:FF:FF:FF
- MAC address profile name - this is the name the user will create to display the mac address in a user friendly way i.e "my intel nuc"
- BroadCast IP address - this is the broadcast address of your respective router, this is where the wake on lan packet will be sent to in order for every machine to see & read the packet (and wake up if its for them) i.e. 192.168.0.255

The CSV file format must be as so for each line:
 
**MAC address**;**MAC address profile name**;**BroadCast IP address**

The semi colon will be the splitter for this information, the mac address must be (currently) seperated with ':' (look in key terms for examples).


## Final Remarks
This program is still in development and plans to add a plethora of features & QOL updates, here is the current list

1. Export files (Saving to a new file)
2. Ability to edit current WOL profiles
3. Improve scaling for main window
4. Allow for more flexibility regarding reading files (allowing a range of splitters instead of just ;)
6. Make application more appealing & modern
