class Nodeitem:

    # Constructor
    def __init__(self, targetID=0, g=0, h=0, idpreviousstate=0):
        self.targetID = targetID
        self.g = g
        self.h = h
        self.f = self.g + self.h
        self.idpreviousstate = idpreviousstate

    # Getters and Setters
    def get_targetID(self):
        return self.targetID

    def set_targetID(self, targetID):
        self.targetID = targetID

    def get_g(self):
        return self.g

    def set_g(self, g):
        self.g = g

    def get_h(self):
        return self.h

    def set_h(self, h):
        self.h = h

    def get_f(self):
        return self.f

    def set_f(self, f):
        self.f = f

    def get_idpreviousstate(self):
        return self.idpreviousstate

    def set_idpreviousnode(self, idpreviousstate):
        self.idpreviousstate = idpreviousstate

    # Write item
    def __str__(self):
        return "[state = {0}, g={1}, h={2}, f={3}, idpreviousstate={4}]".format(self.targetID, self.g, self.h, self.f,
                                                                                self.idpreviousstate)

    def __repr__(self):
        return "[state = {0}, g={1}, h={2}, f={3}, idpreviousstate={4}]".format(self.targetID, self.g, self.h, self.f,
                                                                                self.idpreviousstate)
