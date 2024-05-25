import datetime

import matplotlib.pyplot as plt
import openpyxl
import xlrd
import xlwt
from xlrd import open_workbook

class PropagateItem(object):
    idPos = 0
    positionPos = 2
    mediumPos = 3
    propagateStatusPos = 4
    categoryPos = 5
    speciesPos = 6
    propagateStartTimePos = 7
    processingMethodPos = 8
    sourcePos = 18
    resultPos = 19
    propagateEndTimePos = 20
    pottingResultPos = 21
    analysisPos = 22
    statusDic = {"开始": 0, "生根": 1, "上盆": 2, "上盆成功": 3, "上盆失败": 4, "黑杆": 5, "迁移": 6}
    colorDic = {0: 'k', 1: 'g', 2: 'g', 3: 'g', 4: 'y', 5: 'r', 6: 'b'}

    def __init__(self, row):
        self.row = row

    def getId(self):
        return self.row[self.idPos]

    def getPosition(self):
        """
        return the position of this item
        :return: str
        """
        return self.row[self.positionPos]

    def getMedium(self):
        """
        :return: str
        """
        return self.row[self.mediumPos]

    def getStatus(self):
        """
        :return: int
        """
        return int(self.row[self.propagateStatusPos])

    def getCategory(self):
        """
        :return: str
        """
        return self.row[self.categoryPos]

    def getSpecies(self):
        """
        :return: str
        """
        return self.row[self.speciesPos]

    def getStartTime(self):
        """
        :return: datetime
        """
        startTime = self.row[self.propagateStartTimePos]
        if not startTime:
            return None
        if isinstance(startTime, str):
            print("time should be float not string")
        return xlrd.xldate_as_datetime(startTime, 0)

    def getStartTimeNoYear(self):
        """
        统一为2023年
        :return: datetime
        """
        start = self.getStartTime()
        format = start.strftime("%m-%d")
        return datetime.datetime.strptime(format, "%m-%d")

    def getEndTimeNoYear(self):
        end = self.getEndTime()
        newFormat = end.strftime("%m-%d")
        if self.getStartTime().year != end.year:
            return datetime.datetime.strptime(newFormat, "%m-%d")
        return datetime.datetime.strptime(newFormat, "%m-%d")

    def getProcessingMethod(self):
        """
        :return: str
        """
        return self.row[self.processingMethodPos]

    def getSource(self):
        """
        :return: str
        """
        return self.row[self.sourcePos]

    def getResult(self):
        """
        :return: str
        """
        return self.row[self.resultPos]

    def getEndTime(self):
        """
        :return: datetime
        """
        endTime = self.row[self.propagateEndTimePos]
        if not endTime:
            return self.getStartTime()
        return xlrd.xldate_as_datetime(endTime, 0)

    def getPottingResult(self):
        """
        :return: str
        """
        return self.row[self.pottingResultPos]

    def getAnalysis(self):
        """
        :return: str
        """
        return self.row[self.analysisPos]

    def plot(self, ax, xPos):
        if self.getStatus() is None:
            print("No status.")
            return
        color = self.colorDic[self.getStatus()]
        ax.plot([xPos, xPos], [self.getStartTime(), self.getEndTime()], color=color, marker='o', markersize='1')

    def valid(self, filter):
        """
        :param filter: Filter
        :return: bool
        """

class FilterOut(object):
    def __init__(self):
        pass

class PlantRecord(object):
    def __init__(self, record):
        self.record = record

    def getId(self):
        return self.record[1]

    def toString(self):
        l = [str(self.record[i]) for i in range(14)]
        return " + ".join(l)

plantRecordFile = "/Users/xiaomingqiu/Documents/Gardening/植物养护记录.xls"
plantSheet = open_workbook(plantRecordFile).sheets()[0]
plantRecord = [PlantRecord(plantSheet.row_values(i)) for i in range(5, plantSheet.nrows)]
plantRecordSet = {plant.getId for plant in plantRecord}

propagateRecordFile = "/Users/XiaomingQiu/Documents/Gardening/sortTest.xls"
propagateSheet = open_workbook(propagateRecordFile).sheets()[0]
propagateRecord = [PropagateItem(propagateSheet.row_values(i)) for i in range(3, propagateSheet.nrows)]
for propagateItem in propagateRecord:
    id = propagateItem.getId()
    if id and id > 800 and id not in plantRecordSet:
        print(propagateItem.getId())


