import geb.driver.SauceLabsDriverFactory
import org.openqa.selenium.Platform
import org.openqa.selenium.Proxy
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.LocalFileDetector
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.Point
import org.openqa.selenium.Dimension

enum Browser { chrome, firefox, ie, iexplore }
enum SystemProperties {
  proxy("geb.proxy", null),
  gridurl("geb.grid.URL", "http://ncepsivm11981:4444/wd/hub"),
  screenshot("takeScreenshots","false"),
  timeout("geb.timeout","120")

  String propertyName
  String defaultValue

  SystemProperties(String name, String defaultValue) {
    propertyName = name
    this.defaultValue = defaultValue
  }
  String get() {
    String propertyValue = System.properties[propertyName] ?: System.properties[propertyName.toLowerCase()] ?: defaultValue
    System.out.println( String.format("Value found for property %s = [%s]", propertyName, propertyValue) )
    propertyValue
  }

  def set() {
    System.setProperty(propertyName, get())
  }
}

baseUrl = 'http://www.way2automation.com/angularjs-protractor/webtables/'
reportsDir = "target/surefire-reports"
atCheckWaiting = true
BROWSERHEIGHT = 1024
BROWSERWIDTH = 768

def addProxyToCapabilities(DesiredCapabilities caps) {
  String proxyUrl = SystemProperties.proxy.get()
  if (proxyUrl) {
    Proxy proxy = new Proxy()
    proxy.setProxyType(Proxy.ProxyType.MANUAL)
    proxy.setFtpProxy(proxyUrl)
    proxy.setHttpProxy(proxyUrl)
    proxy.setSslProxy(proxyUrl)
    caps.setCapability(CapabilityType.PROXY, proxy)
  }
}

def createDriverInstance(DesiredCapabilities capabilities, Browser browser) {
  switch (browser) {
    case Browser.firefox:
      DriverInstance = new FirefoxDriver(capabilities)
      break
    case Browser.iexplore:
    case Browser.ie:
      DriverInstance = new InternetExplorerDriver(capabilities)
      break
    case Browser.chrome:
    default:
      DriverInstance = new ChromeDriver(capabilities)
  }
}

def getDesiredCapabilities(String defaultBrowserName) {
  switch (defaultBrowserName) {
    case Browser.iexplore.name():
    case Browser.ie.name():
      DesiredCapabilities.internetExplorer()
      break
    case Browser.firefox.name():
      DesiredCapabilities.firefox()
      break
    case Browser.chrome.name():
    default:
      DesiredCapabilities.chrome()
  }
}

def driverInstanceConfig(driverInstance) {

  driverInstance.manage().window().setPosition(new Point(0, 0))
  driverInstance.manage().window().setSize(new Dimension(BROWSERHEIGHT, BROWSERWIDTH))
  driverInstance.manage().window().maximize()
  driverInstance
}

driver = {
  System.setProperty('webdriver.chrome.driver', 'C:\\drivers\\chromedriver.exe')
  DesiredCapabilities capabilities = DesiredCapabilities.chrome()
  // Option --test-type disable strict certificate validation
  ChromeOptions options = new ChromeOptions()
  options.addArguments("--test-type")
  capabilities.setCapability(ChromeOptions.CAPABILITY, options)

  addProxyToCapabilities(capabilities)
  RemoteWebDriver driverInstance = createDriverInstance(capabilities, Browser.chrome)
  driverInstanceConfig(driverInstance)

}

waiting {
  timeout = 45
  retryInterval = 0.5
  includeCauseInMessage = true
  presets {
    veryslow {
      timeout = 120
      retryInterval = 0.5
    }
    slow {
      timeout = 60
      retryInterval = 0.5
    }
    quick {
      timeout = 15
      retryInterval = 0.1
    }
    veryquick {
      timeout = 2
      retryInterval = 0.1
    }
  }
}

//cacheDriver = false

environments {

  // run as maven -Dgeb.env=chrome test
  // See: http://code.google.com/p/selenium/wiki/ChromeDriver
  chrome {
    driver = {
      System.setProperty('webdriver.chrome.driver', 'C:\\drivers\\chromedriver.exe')
      DesiredCapabilities capabilities = DesiredCapabilities.chrome()
      // Option --test-type disable strict certificate validation
      ChromeOptions options = new ChromeOptions()
      options.addArguments("--test-type")
      capabilities.setCapability(ChromeOptions.CAPABILITY, options)

      addProxyToCapabilities(capabilities)
      RemoteWebDriver driverInstance = createDriverInstance(capabilities, Browser.chrome)
      driverInstanceConfig(driverInstance)
    }
  }

  firefox {
    driver = {
      System.setProperty('webdriver.firefox.driver', 'C:\\drivers\\geckodriver.exe')
      DesiredCapabilities capabilities = DesiredCapabilities.firefox()
      addProxyToCapabilities(capabilities)
      RemoteWebDriver driverInstance = createDriverInstance(capabilities, Browser.firefox)
      driverInstanceConfig(driverInstance)
    }
  }

  ie {
    driver = {
      waiting {
        includeCauseInMessage = true
        timeout = 120
        retryInterval = 2
        presets {
          veryslow {
            timeout = 190
          }
          slow {
            timeout = 130
          }
          quick {
            timeout = 60
            retryInterval = 1
          }
          veryquick {
            timeout = 20
            retryInterval = 1
          }
        }
      }
      DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer()
      addProxyToCapabilities(capabilities)
      RemoteWebDriver driverInstance = createDriverInstance(capabilities, Browser.ie)
      driverInstanceConfig(driverInstance)
    }
  }

  localgrid {
    waiting {
      includeCauseInMessage = true
      timeout = 60
      retryInterval = 1
      presets {
        slow {
          timeout = 90
          retryInterval = 1
        }
        quick {
          timeout = 40
          retryInterval = 0.1
        }
      }
    }
    driver = {
      SystemProperties.screenshot.set()
      DesiredCapabilities capabilities = getDesiredCapabilities( SystemProperties.gebsaucelab_browser.get() )
      //capabilities.setCapability(CapabilityType.PLATFORM, DEFAULT_PLATFORM)
      RemoteWebDriver driverInstance
      switch ( SystemProperties.gebsaucelab_browser.get() ) {
        case Browser.iexplore.name():
        case Browser.ie.name():
          capabilities.setCapability(CapabilityType.PLATFORM, Platform.VISTA)
          break
        case Browser.chrome.name():
          ChromeOptions options = new ChromeOptions()
          options.addArguments("--test-type")
          capabilities.setCapability(ChromeOptions.CAPABILITY, options)
          break
      }
      System.out.println("requesting for these capabilities : " + capabilities.toString() )
      driverInstance = new RemoteWebDriver(new URL(SystemProperties.gridurl.get()), capabilities)
      driverInstance.setFileDetector(new LocalFileDetector())
      driverInstanceConfig(driverInstance)
    }
  }
}