class Node:

    # Constructor
    def __init__(self, targetID=0, boolType=False, succesorsid=[]):
        self.targetID = targetID
        self.boolType=boolType # 1 if final or accepting state else 0
        self.succesorsid = succesorsid

    # Getters and Setters
    def get_targetID(self):
        return self.targetID

    def set_targetID(self, targetID):
        self.targetID = targetID

    def get_succesorsid(self):
        return self.succesorsid

    def set_succesorsid(self, succesorsid):
        self.succesorsid = succesorsid

    def get_boolType(self):
        return self.boolType

    def set_boolType(self, boolType):
        self.boolType = boolType

    # Check if the node has neighbord
    def indamissibleNode(self):
        return len(self.succesorsid) == 0

    # Write Output
    def __str__(self):
        return "[state = {0}, bool={1}, succesors ={2}]".format(self.targetID, self.boolType, self.succesorsid)

    def __repr__(self):
        return "[state = {0}, bool={1}, succesors ={2}]".format(self.targetID, self.boolType, self.succesorsid)
