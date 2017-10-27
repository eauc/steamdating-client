require "rspec/expectations"

module Pages
  NAV = ".sd-nav"
  PAGE_MENU = ".sd-page-menu"
  PAGE_CONTENT = ".sd-page-content"

  class Page
    include RSpec::Matchers
    include Capybara::DSL

    # def loaded?
    #   url_regexp = Regexp.new("/#/#{@route}$")
    #   page.has_current_path?(url_regexp, url: true)
    # end
  end
end
