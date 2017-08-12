require_relative "./page"

module Pages
  class File < Page
    def initialize
      @route = "file"
    end

    def open(file)
      within(".sd-PageContent") do
        file_path = ::File.absolute_path(::File.join(::File.dirname(__FILE__), "../data/", file))
        attach_file("Open", file_path, visible: false)
      end
      self
    end
  end
end
