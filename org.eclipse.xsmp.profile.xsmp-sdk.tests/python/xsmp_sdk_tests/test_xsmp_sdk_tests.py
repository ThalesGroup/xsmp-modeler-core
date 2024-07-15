import ecss_smp
import xsmp


class XsmpSdkTest(xsmp.unittest.TestCase):
    
    def loadAssembly(self, sim: ecss_smp.Smp.ISimulator):
        sim.LoadLibrary("xsmp_sdk_tests")

    def test_LibraryLoading(self):
        pass
        
        
