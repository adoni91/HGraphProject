class Nodeitem(object):

    # Constructor
    def __init__(self, targetID=0, g=0, h=0, idpreviousnode=0):
        self.targetID = targetID
        self.g = g
        self.h = h
        self.f = self.g + self.h
        self.idpreviousnode = idpreviousnode

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

    def get_idpreviousnode(self):
        return self.idpreviousnode

    def set_idpreviousnode(self, idpreviousnode):
        self.idpreviousnode = idpreviousnode

    # Write item
    def __str__(self):
        return "[state = {0}, g={1}, h={2}, f={3}, idpreviousnode={4}]".format(self.targetID, self.g, self.h, self.f,
                                                                               self.idpreviousnode)

    def __repr__(self):
        return "[state = {0}, g={1}, h={2}, f={3}, idpreviousnode={4}]".format(self.targetID, self.g, self.h, self.f,
                                                                               self.idpreviousnode)
