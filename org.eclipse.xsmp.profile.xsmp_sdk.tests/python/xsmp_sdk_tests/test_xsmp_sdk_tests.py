import ecss_smp
import xsmp
import Demo3


class XsmpSdkTest(xsmp.unittest.TestCase):
    try:
        sim: xsmp_sdk_tests._test_xsmp_sdk_tests.Simulator
    except AttributeError:
        pass
    
    def loadAssembly(self, sim: ecss_smp.Smp.ISimulator):
        sim.LoadLibrary("xsmp_sdk_tests")

    def test_Demo3(self):
        pass
        
        
