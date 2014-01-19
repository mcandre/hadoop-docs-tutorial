guard :shell do
  watch(/.+\.java/) do |m|
    title = 'Compile'
    eager 'make'
    status = ($?.success? && :success) || :failed
    n '', title, status
    ''
  end
end
