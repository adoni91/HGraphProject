class Node:

    # Constructor
    def __init__(self, targetID=0, succesors=[]):
        self.targetID = targetID
        self.succesors = succesors

    # Getters and Setters
    def get_targetID(self):
        return self.targetID

    def set_targetID(self, targetID):
        self.targetID = targetID

    def get_succesors(self):
        return self.succesors

    def set_succesors(self, succesors):
        self.succesors = succesors

    # Check if the node has neighbord
    def indamissibleNode(self):
        return len(self.succesors) == 0

    # Write Output
    def __str__(self):
        return "node = {0}, succesors ={1}".format(self.targetID, self.succesors)

    def __repr__(self):
        return "node = {0}, succesors ={1}".format(self.targetID, self.succesors)
