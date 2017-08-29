require "rspec/expectations"

module Pages
  NAV = ".sd-Nav"
  PAGE_MENU = ".sd-PageMenu"
  PAGE_CONTENT = ".sd-PageContent"

  class Page
    include RSpec::Matchers
    include Capybara::DSL

    def load
      visit "/#/#{@route}"
      self
    end

    def loaded?
      url_regexp = Regexp.new("/#/#{@route}$")
      page.has_current_path?(url_regexp, url: true)
    end
  end
end
